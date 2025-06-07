package com.insysred.isp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SexoValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSexo {
    String message() default "Sexo inválido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
