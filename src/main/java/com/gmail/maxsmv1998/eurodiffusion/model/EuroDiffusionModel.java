package com.gmail.maxsmv1998.eurodiffusion.model;

import com.gmail.maxsmv1998.eurodiffusion.util.validator.CountryValidatorManager;

import java.util.List;

import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COORDINATE;
import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COUNTRIES;

public class EuroDiffusionModel {
    private int[][] countriesMap;

    public EuroDiffusionModel(List<CountryModel> countries) {
        validateCountries(countries);
        buildCountriesMap(countries);
    }

    private void validateCountries(List<CountryModel> countries) {
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

    private void buildCountriesMap(List<CountryModel> countries) {
        countriesMap = new int[MAX_COORDINATE][MAX_COORDINATE];
        countries.forEach(country -> {

        });
    }
}
