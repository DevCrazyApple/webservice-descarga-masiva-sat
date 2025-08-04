package com.ws.request_service.infrastructure.client.parser;

import com.ws.request_service.infrastructure.client.parser.enums.BaseCodes;
import com.ws.request_service.infrastructure.client.parser.enums.CustomCode;
import com.ws.request_service.infrastructure.client.parser.enums.Solicitud;

public class CustomCodeResolver {
    public static CustomCode resolveCode(int code) {
        CustomCode result;

        result = CustomCode.fromCode(BaseCodes.class, code);
        if (result != null) return result;

        result = CustomCode.fromCode(Solicitud.class, code);
        if (result != null) return result;

        // Puedes agregar más enums aquí si los tienes

        return BaseCodes.Unknow; // código desconocido como fallback
    }

}
