package com.gmail.maxsmv1998;

import com.gmail.maxsmv1998.eurodiffusion.model.CountryModel;
import com.gmail.maxsmv1998.eurodiffusion.model.EuroDiffusionModel;
import com.gmail.maxsmv1998.eurodiffusion.util.InputReader;

import java.util.List;

public class Runner {
    public static final String ERROR_COORDINATE_PATTERN = "Invalid {0}. It should be 1 ≤ {0} ≤ 10.";

    public static void main(String[] args) {
        List<CountryModel> countries = InputReader.getInputData("case1.txt");
        new EuroDiffusionModel(countries);
    }

}
