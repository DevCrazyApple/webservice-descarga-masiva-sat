package com.ws.status_service.infrastructure.client.parser.enums;

public enum Solicitud implements CustomCode {
    Ok(5000, "Solicitud de descarga recibida con éxito"),
    Unauthorized(5001, "Tercero no autorizado", "El solicitante no tiene autorización de descarga de xml de los contribuyentes"),
    Exhausted(5002, "Se han agotado las solicitudes de por vida", "Se ha alcanzado el límite de solicitudes, con el mismo criterio"),
    Duplicate(5005, "Ya se tiene una solicitud registrada", "Ya existe una solicitud activa con los mismos criterios"),
    InternalError(5006, "Error interno en el proceso"),
    NotFound(5004, "No se encontró la información", "Indica que la solicitud de descarga que se está verificando no generó paquetes por falta de información.");

    private final int code;
    private final String message;
    private final String description;

    Solicitud(int code, String message) {
        this(code, message, null);
    }

    Solicitud(int code, String message, String description) {
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

    public static Solicitud fromCode(int code) {
        for (Solicitud c : values()) {
            if (c.code == code) return c;
        }
        return null;
    }
}

