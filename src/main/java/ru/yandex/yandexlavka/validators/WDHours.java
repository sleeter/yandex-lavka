package ru.yandex.yandexlavka.validators;

import jakarta.validation.*;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = WDHoursValidator.class)
@Documented
public @interface WDHours {
    String message() default "{WorkingHours.invalid}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
