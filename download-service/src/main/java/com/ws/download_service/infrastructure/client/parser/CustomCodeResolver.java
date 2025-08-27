package com.ws.download_service.infrastructure.client.parser;

import com.ws.status_service.infrastructure.client.parser.enums.*;

public class CustomCodeResolver {
    public static CustomCode resolveCode(int code) {
        CustomCode result;

        result = CustomCode.fromCode(BaseCodes.class, code);
        if (result != null) return result;

        result = CustomCode.fromCode(Solicitud.class, code);
        if (result != null) return result;

        result = CustomCode.fromCode(Verificar.class, code);
        if (result != null) return result;

        result = CustomCode.fromCode(VerificarStatus.class, code);
        if (result != null) return result;

        return BaseCodes.Unknow; // código desconocido como fallback
    }
}
