package com.gmail.maxsmv1998.eurodiffusion.data;

import lombok.Value;

import java.util.List;
import java.util.Objects;

@Value
public class TestCaseData {
    List<CountryData> countries;

    public TestCaseData(List<CountryData> countries) {
        requireNotEmpty(countries);
        this.countries = List.copyOf(countries);
    }

    private void requireNotEmpty(List<CountryData> countries) {
        if (Objects.isNull(countries) || countries.isEmpty()) {
            throw new IllegalArgumentException("Test case should contain at least 1 country!");
        }
    }
}
