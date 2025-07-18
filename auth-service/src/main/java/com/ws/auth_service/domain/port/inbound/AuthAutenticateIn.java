package com.ws.auth_service.domain.port.inbound;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface AuthAutenticateIn {
    String authenticate(X509Certificate cert, PrivateKey key) throws Exception;
}
