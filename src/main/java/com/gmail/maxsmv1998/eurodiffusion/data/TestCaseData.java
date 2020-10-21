package com.gmail.maxsmv1998.eurodiffusion.data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class TestCaseData implements Serializable {
    private static final long serialVersionUID = 3336729775932286194L;

    private final List<CountryData> countries;

    public TestCaseData(List<CountryData> countries) {
        requireNotEmpty(countries);
        this.countries = List.copyOf(countries);
    }

    public List<CountryData> getCountries() {
        return List.copyOf(countries);
    }

    private void requireNotEmpty(List<CountryData> countries) {
        if (Objects.isNull(countries) || countries.isEmpty()) {
            throw new IllegalArgumentException("Test case should contain at least 1 country!");
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TestCaseData.class.getSimpleName() + "[", "]")
                .add("countries=" + countries)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestCaseData that = (TestCaseData) o;
        return Objects.equals(countries, that.countries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countries);
    }
}
