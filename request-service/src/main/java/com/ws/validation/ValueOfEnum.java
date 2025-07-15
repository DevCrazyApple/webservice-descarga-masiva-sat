package com.ws.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValueOfEnumValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();
    String message() default "Valor inválido. Debe ser uno de los valores del enum.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
