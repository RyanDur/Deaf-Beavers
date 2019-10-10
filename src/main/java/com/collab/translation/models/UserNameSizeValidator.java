package com.collab.translation.models;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserNameSizeValidator implements ConstraintValidator<MaxSizeUserName, String> {

    @Value("${name.max.length}")
    private Integer maxLength;

    public void initialize(MaxSizeUserName constraint) {
    }

    public boolean isValid(String userName, ConstraintValidatorContext context) {
        return userName.length() <= maxLength;
    }
}
