package com.collab.translation.models;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = UserNameSizeValidator.class)
@Documented
public @interface MaxSizeUserName {
    String message() default "NAME_EXCEEDS_MAX_SIZE";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
