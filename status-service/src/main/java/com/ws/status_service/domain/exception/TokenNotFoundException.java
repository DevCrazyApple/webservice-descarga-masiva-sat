package com.ws.status_service.domain.exception;

public class TokenNotFoundException extends RuntimeException {
    public TokenNotFoundException(String rfc) {
        super("Token no encontrado en Redis para RFC: " + rfc);
    }
}
