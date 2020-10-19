package com.gmail.maxsmv1998.eurodiffusion.util.validator;

import com.gmail.maxsmv1998.eurodiffusion.model.CountryModel;
import com.gmail.maxsmv1998.eurodiffusion.util.validator.impl.NumberValidator;
import com.gmail.maxsmv1998.eurodiffusion.util.validator.impl.RegexValidator;

import java.util.ArrayList;
import java.util.List;

import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COORDINATE;
import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MAX_COUNTRY_NAME_LENGTH;
import static com.gmail.maxsmv1998.eurodiffusion.constant.InputRestrictions.MIN_COORDINATE;

public class CountryValidatorManager {
    public static final RegexValidator nameValidator = new RegexValidator("^[^\\s]+$", MAX_COUNTRY_NAME_LENGTH);
    public static final NumberValidator coordinateValidator = new NumberValidator(MIN_COORDINATE, MAX_COORDINATE);

    public static final String ERROR_NAME = "Invalid name. Name is a single word with at most 25 characters.";
    public static final String ERROR_COORDINATE_PATTERN = "Invalid {0}. 1 ≤ {0} ≤ 10.";

    private CountryValidatorManager() {
    }

    public static List<String> validate(CountryModel country) {
        List<String> errors = new ArrayList<>();

        if (nameValidator.isInvalid(country.getName())) {
            errors.add(ERROR_NAME);
        }
        if (coordinateValidator.isInvalid(country.getXL())) {
            errors.add(getCoordinateError("xl"));
        }
        if (coordinateValidator.isInvalid(country.getYL())) {
            errors.add(getCoordinateError("yl"));
        }
        if (coordinateValidator.isInvalid(country.getXH())) {
            errors.add(getCoordinateError("xh"));
        }
        if (coordinateValidator.isInvalid(country.getYH())) {
            errors.add(getCoordinateError("yh"));
        }
        if (country.getXL() > country.getXH()) {
            errors.add("Invalid coordinate values. xl ≤ xh");
        }
        if (country.getYL() > country.getYH()) {
            errors.add("Invalid coordinate values. yl ≤ yh");
        }

        return errors;
    }

    private static String getCoordinateError(String coordinateName) {
        return ERROR_COORDINATE_PATTERN.replace("{0}", coordinateName);
    }

}
