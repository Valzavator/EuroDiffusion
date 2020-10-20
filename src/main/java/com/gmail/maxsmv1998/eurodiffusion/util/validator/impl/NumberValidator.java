package com.gmail.maxsmv1998.eurodiffusion.util.validator.impl;

import com.gmail.maxsmv1998.eurodiffusion.util.validator.Validator;

import java.util.Objects;

public class NumberValidator implements Validator<Integer> {
    private final Integer minValue;
    private final Integer maxValue;

    public NumberValidator(Integer minValue, Integer maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public boolean isValid(Integer number) {
        return Objects.nonNull(number)
                && minValue.compareTo(number) <= 0
                && maxValue.compareTo(number) >= 0;
    }
}
