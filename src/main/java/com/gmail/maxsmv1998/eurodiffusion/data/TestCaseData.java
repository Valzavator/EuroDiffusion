package com.gmail.maxsmv1998.eurodiffusion.data;

import lombok.Value;

import java.util.List;
import java.util.Objects;

@Value
public class TestCaseData {
    int number;
    List<InputCountryData> countries;

    public TestCaseData(int number, List<InputCountryData> countries) {
        requireNotEmpty(countries);
        this.number = number;
        this.countries = List.copyOf(countries);
    }

    private void requireNotEmpty(List<InputCountryData> countries) {
        if (Objects.isNull(countries) || countries.isEmpty()) {
            throw new IllegalArgumentException("Test case should contain at least 1 country!");
        }
    }
}
