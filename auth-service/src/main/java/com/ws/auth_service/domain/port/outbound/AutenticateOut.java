package com.ws.auth_service.domain.port.outbound;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface AutenticateOut {
    String authenticate(X509Certificate cert, PrivateKey key) throws Exception;
}
