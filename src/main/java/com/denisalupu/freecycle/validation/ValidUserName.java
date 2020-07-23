package com.denisalupu.freecycle.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UserNameValidator.class)
@Documented
public @interface ValidUserName {
    String message() default "Invalid user name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

