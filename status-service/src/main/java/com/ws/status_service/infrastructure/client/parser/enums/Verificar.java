package com.ws.status_service.infrastructure.client.parser.enums;

public enum Verificar implements CustomCode {
    OK(5000, "Solicitud recibida con éxito", null),
    LIMIT(5003, "Tope máximo de elementos de la consulta", "La solicitud sobrepasa el máximo de resultados por tipo de solicitud (Metadata y CFDI)"),
    NOT_FOUND(5004, "No se encontró la información", "No se encontró la información de la solicitud de descarga que se pretende verificar."),
    LIMIT_FOIL(5011, "Límite de descargas por folio por día", "Se ha alcanzado o sobrepasado el límite de descargas diarias por folio.");

    private final int code;
    private final String message;
    private final String description;

    Verificar(int code, String message, String description) {
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

