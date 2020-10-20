package com.gmail.maxsmv1998.eurodiffusion.model;

import com.gmail.maxsmv1998.eurodiffusion.data.CountryData;
import com.gmail.maxsmv1998.eurodiffusion.util.validator.CountryValidatorManager;

import java.util.List;
import java.util.Objects;

import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COORDINATE;
import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COUNTRIES;
import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MIN_COORDINATE;

public class EuroDiffusionModel {
    private final List<CountryData> countries;
    private MapModel countriesMap;

    public EuroDiffusionModel(List<CountryData> countries) {
        validateCountries(countries);
        this.countries = List.copyOf(countries);

        buildCountriesMap();
    }

    private void validateCountries(List<CountryData> countries) {
        if (countries.size() > MAX_COUNTRIES) {
            throw new IllegalArgumentException("The maximum number of countries allowed is " +
                    MAX_COUNTRIES + ". But given " + countries.size() + "instead.");
        }
        countries.forEach(country -> {
            List<String> errors = CountryValidatorManager.validate(country);
            if (!errors.isEmpty()) {
                throw new IllegalArgumentException("Invalid country [" + country + "]. Errors: " + errors);
            }
        });
    }

    private void buildCountriesMap() {
        int lengthX = countries.stream().mapToInt(CountryData::getXH).max().orElse(MAX_COORDINATE);
        int lengthY = countries.stream().mapToInt(CountryData::getYH).max().orElse(MAX_COORDINATE);
        countriesMap = new MapModel(lengthX, lengthY);
        countries.forEach(country -> {
            for (int x = country.getXL(); x <= country.getXH(); x++) {
                for (int y = country.getYL(); y <= country.getYH(); y++) {
                    String returnedValue = countriesMap.set(x, y, country.getName());
                    if (Objects.nonNull(returnedValue)) {
                        throw new IllegalArgumentException("Invalid countries coordinates. " +
                                country.getName() + " intersects with " + returnedValue + " at [" + x + "," + y + "]");
                    }
                }
            }
        });
        System.out.println(countriesMap);
        checkConnectionsBetweenCountries();
    }

    private void checkConnectionsBetweenCountries() {
        if (countries.size() == 1) {
            return;
        }
        // TO DO - check neighbours

        for (int x = MIN_COORDINATE; x <= countriesMap.getLengthX(); x++) {
            for (int y = MIN_COORDINATE; y <= countriesMap.getLengthY(); y++) {
//                List<String> neighbors = countriesMap.getNeighboringCities(x, y);
//                if (neighbors.isEmpty())
            }
        }
    }

}
