package ru.yandex.yandexlavka.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.regex.Pattern;

public class WDHoursValidator implements ConstraintValidator<WDHours, List<String>> {
    private static final Pattern MINUTES_HOURS =
            Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]-(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$");
    public static boolean isMinutesHoursPattern(String s) {
        return MINUTES_HOURS.matcher(s).matches();
    }
    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) return false;
        for(String s : value) {
            if(!isMinutesHoursPattern(s)) {
                return false;
            }
        }
        return true;
    }
}
