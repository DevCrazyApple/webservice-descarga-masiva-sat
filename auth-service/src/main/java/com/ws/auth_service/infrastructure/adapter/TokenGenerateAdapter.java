package com.ws.auth_service.infrastructure.adapter;

import com.ws.auth_service.domain.model.AuthModel;
import com.ws.auth_service.domain.port.outbound.TokenGeneratorOut;
import com.ws.auth_service.infrastructure.adapter.redis.TokenCacheAdapter;
import com.ws.auth_service.infrastructure.client.SatAuthenticationService;
import com.ws.auth_service.infrastructure.entities.AuthEntity;
import com.ws.auth_service.infrastructure.repository.AuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.Optional;

import static com.ws.auth_service.infrastructure.client.util.CryptoUtils.*;

@Slf4j
@Component
public class TokenGenerateAdapter implements TokenGeneratorOut {

    private final SatAuthenticationService satAuthenticationService;
    private final AuthRepository authRepository;
    private final TokenCacheAdapter tokenCacheAdapter;

    public TokenGenerateAdapter(SatAuthenticationService satAuthenticationService, AuthRepository authRepository, TokenCacheAdapter tokenCacheAdapter) {
        this.satAuthenticationService = satAuthenticationService;
        this.authRepository = authRepository;
        this.tokenCacheAdapter = tokenCacheAdapter;
    }

    @Override
    public AuthModel generateToken(String certificate, String privateKey) throws Exception {

        X509Certificate cert = generateCertificateFromDER(parseDERFromPEM(certificate));
        RSAPrivateKey key = generatePrivateKeyFromDER(parseDERFromPEM(privateKey));

        String rfc = this.satAuthenticationService.extractRfc(cert);
        log.info("**** rfc entry {}", rfc);

        // preguntar en redis si persiste el token solo tiene duración de 5 min

        String token = "WRAP access_token=\"" + this.satAuthenticationService.authenticate(cert, key) + "\"";
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
}
