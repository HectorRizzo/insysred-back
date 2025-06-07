package com.insysred.isp.validation;

import com.insysred.isp.enums.Sexo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.logging.Logger;

public class SexoValidator implements ConstraintValidator<ValidSexo, Sexo> {
    @Override
    public void initialize(ValidSexo constraintAnnotation) {
    }

    @Override
    public boolean isValid(Sexo sexoField, ConstraintValidatorContext constraintValidatorContext) {
        if (sexoField == null || sexoField.equals("")){
            return true;
        }

        for (Sexo sexo : Sexo.values()) {
            if (sexo.equals(sexoField)) {
                return true;
            }
        }

        return false;
    }
}