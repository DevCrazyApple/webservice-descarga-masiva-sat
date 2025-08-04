package com.ws.status_service.infrastructure.client.parser.enums;

public interface CustomCode {
    int getCode();
    String getMessage();
    String getDescription();

    static <T extends Enum<T> & CustomCode> T fromCode(Class<T> enumClass, int code) {
        for (T constant : enumClass.getEnumConstants()) {
            if (constant.getCode() == code) return constant;
        }
        return null;
    }
}
