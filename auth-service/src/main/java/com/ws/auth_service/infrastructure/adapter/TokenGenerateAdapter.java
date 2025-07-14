package com.ws.auth_service.infrastructure.adapter;

import com.ws.auth_service.domain.model.AuthModel;
import com.ws.auth_service.domain.port.outbound.AuthAutenticateOut;
import com.ws.auth_service.domain.port.outbound.TokenGeneratorOut;
import com.ws.auth_service.infrastructure.adapter.redis.TokenCacheAdapter;
import com.ws.auth_service.infrastructure.client.SoapClient;
import com.ws.auth_service.infrastructure.client.builder.XmlBuilder;
import com.ws.auth_service.infrastructure.client.parser.ResponseParser;
import com.ws.auth_service.infrastructure.entities.AuthEntity;
import com.ws.auth_service.infrastructure.repository.AuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

import static com.ws.auth_service.infrastructure.client.util.CryptoUtils.*;

@Slf4j
@Component
public class TokenGenerateAdapter implements TokenGeneratorOut, AuthAutenticateOut {

//    private final AuthSatService authSatService;
    private final AuthRepository authRepository;
    private final TokenCacheAdapter tokenCacheAdapter;
    private final SoapClient client;
    private final XmlBuilder builder;
    private final ResponseParser parser;

    public TokenGenerateAdapter(AuthRepository authRepository, TokenCacheAdapter tokenCacheAdapter, SoapClient client, XmlBuilder builder, ResponseParser parser) {
        this.authRepository = authRepository;
        this.tokenCacheAdapter = tokenCacheAdapter;
        this.client = client;
        this.builder = builder;
        this.parser = parser;
    }

    @Override
    public AuthModel generateToken(String certificate, String privateKey) throws Exception {

        X509Certificate cert = generateCertificateFromDER(parseDERFromPEM(certificate));
        RSAPrivateKey key = generatePrivateKeyFromDER(parseDERFromPEM(privateKey));

        String rfc = this.extractRfc(cert);
        log.info("**** rfc entry {}", rfc);

        // preguntar en redis si persiste el token solo tiene duración de 5 min

        String token = "WRAP access_token=\"" + this.authenticate(cert, key) + "\"";
        log.info("**** token: {}", token);

        // save mondongo
        AuthEntity record = this.authRepository.saveOrUpdateByRfc(rfc, token);

        // save redis
        this.tokenCacheAdapter.saveToken(record.getRfc(), record.getToken());

        return mapToAuthModel(record);
    }

    private AuthModel mapToAuthModel(AuthEntity authEntity) {
        return  new AuthModel(
                authEntity.getId(),
                authEntity.getToken(),
                authEntity.getRfc(),
                authEntity.getTimestamp()
        );
    }

    @Override
    public String getToken(String rfc) {

//        // ✅ Consultar en Redis primero
//        Optional<String> tokenOptional = tokenCacheAdapter.getToken(rfc);
//        if (tokenOptional.isPresent()) {
//            System.out.println("**** token desde Redis");
//            return tokenOptional.get();
//        }
//        return tokenOptional.orElseThrow();

        return this.tokenCacheAdapter.getToken(rfc)
                .orElseThrow(() -> new IllegalStateException("Token no encontrado en Redis para RFC: " + rfc));


//        AuthEntity record = this.authRepository.findByRfc(rfc);
//        return record.getToken();
    }

    @Override
    public String authenticate(X509Certificate cert, PrivateKey key) throws Exception {
        String request = this.builder.build(cert, key);
        log.info("**** request:\n{}", request);

        String response = this.client.send(request, null);
        log.info("**** response:\n{}", response);

        return decodeValue(this.parser.extractToken(response));
    }

    @Override
    public String extractRfc(X509Certificate certificate) {
        return this.parser.extractRfc(certificate);
    }
}
