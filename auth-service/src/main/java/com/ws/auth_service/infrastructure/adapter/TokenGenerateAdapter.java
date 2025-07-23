package com.ws.auth_service.infrastructure.adapter;

import com.ws.auth_service.domain.model.AuthModel;
import com.ws.auth_service.domain.model.PfxModel;
import com.ws.auth_service.domain.port.outbound.AuthAutenticateOut;
import com.ws.auth_service.domain.port.outbound.TokenGeneratorOut;
import com.ws.auth_service.infrastructure.entities.PfxEntity;
import com.ws.auth_service.infrastructure.redis.TokenCacheAdapter;
import com.ws.auth_service.infrastructure.client.SoapClient;
import com.ws.auth_service.infrastructure.client.builder.XmlBuilder;
import com.ws.auth_service.infrastructure.client.parser.ResponseParser;
import com.ws.auth_service.infrastructure.entities.AuthEntity;
import com.ws.auth_service.infrastructure.repository.AuthRepo;
import com.ws.auth_service.infrastructure.repository.PfxRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;

import static com.ws.auth_service.infrastructure.client.util.CryptoUtils.*;

@Slf4j
@Component
public class TokenGenerateAdapter implements TokenGeneratorOut, AuthAutenticateOut {

    private final AuthRepo authRepository;
    private final PfxRepo pfxRepo;
    private final TokenCacheAdapter tokenCacheAdapter;
    private final SoapClient client;
    private final XmlBuilder builder;
    private final ResponseParser parser;

    public TokenGenerateAdapter(AuthRepo authRepository, PfxRepo pfxRepo, TokenCacheAdapter tokenCacheAdapter, SoapClient client, XmlBuilder builder, ResponseParser parser) {
        this.authRepository = authRepository;
        this.pfxRepo = pfxRepo;
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
        AuthEntity record = this.authRepository.saveOrUpdateByRfc(
            rfc, token,
            Base64.getEncoder().encodeToString(cert.getEncoded()),
            Base64.getEncoder().encodeToString(key.getEncoded())
        );

        // save redis
        this.tokenCacheAdapter.saveToken(record.getRfc(), record.getToken());

        return mapToAuthModel(record);
    }


    @Override
    public String authenticate(X509Certificate cert, PrivateKey key) throws Exception {
        String request = this.builder.build(cert, key);
        log.info("**** request:\n{}", request);

        String response = this.client.send(request, null);
        log.info("**** response:\n{}", response);

        return decodeValue(this.parser.extractToken(response));
    }

    private AuthModel mapToAuthModel(AuthEntity authEntity) {
        return  new AuthModel(
            authEntity.getId(),
            authEntity.getToken(),
            authEntity.getRfc(),
            authEntity.getTimestamp()
        );
    }

    public String extractRfc(X509Certificate certificate) {
        return this.parser.extractRfc(certificate);
    }

    @Override
    public PfxModel getPfx(String rfc) {
        return toPfxModel(this.pfxRepo.findPfxByRfc(rfc));
    }

    private PfxModel toPfxModel(PfxEntity pfxEntity) {
        return new PfxModel(
            pfxEntity.getCert(),
            pfxEntity.getKey()
        );
    }

}
