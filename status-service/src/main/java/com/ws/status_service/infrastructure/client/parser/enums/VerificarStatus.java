package com.ws.status_service.infrastructure.client.parser.enums;

public enum VerificarStatus implements CustomCode {
    Ok(1, "Solicitud Aceptada", null),
    Processing(2, "Procesando la solicitud", null),
    Finished(3, "Proceso terminado", null),
    Error(4, "Error durante el proceso", null),
    Rejected(5, "Solicitud Rechazada", null),
    Expire(6, "Solicitud Vencida", null),
    Unknow(10, "Status desconocido", null);

    private final int code;
    private final String message;
    private final String description;

    VerificarStatus(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static VerificarStatus fromCode(int code) {
        for (VerificarStatus c : values()) {
            if (c.code == code) return c;
        }
        return null;
    }
}
