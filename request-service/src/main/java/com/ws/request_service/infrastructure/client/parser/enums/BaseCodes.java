package com.ws.request_service.infrastructure.client.parser.enums;

public enum BaseCodes implements CustomCode {
    InvalidUser(300, "Usuario ó Token no válido"),
    MalformedXML(301, "XML Mal Formado", "Este código de error se regresa cuando el request posee información invalida"),
    MalformedSeal(302, "Sello Mal Formado"),
    SealRfcMismatch(303, "Sello no corresponde con RfcSolicitante"),
    RevokedOrExpiredCert(304, "Certificado Revocado o Caduco", "El certificado fue revocado, o bien, la fecha de vigencia expiró"),
    InvalidCert(305, "Certificado Inválido", "El certificado puede ser invalido por múltiples razones como son el tipo, la vigencia, etc"),
    Unknow(100, "Código desconocido");

    private final int code;
    private final String message;
    private final String description;

    BaseCodes(int code, String message) {
        this(code, message, null);
    }

    BaseCodes(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    @Override
    public int getCode() { return code; }

    @Override
    public String getMessage() { return message; }

    @Override
    public String getDescription() { return description; }


}
