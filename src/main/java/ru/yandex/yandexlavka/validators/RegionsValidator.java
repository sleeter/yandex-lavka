package ru.yandex.yandexlavka.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class RegionsValidator implements ConstraintValidator<Regions, List<Integer>> {

    @Override
    public boolean isValid(List<Integer> value, ConstraintValidatorContext context) {
        if(value == null || value.isEmpty()) return false;
        for(Integer integer : value) {
            if(integer <= 0) {
                return false;
            }
        }
        return true;
    }
}
