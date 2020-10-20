package com.gmail.maxsmv1998;

import com.gmail.maxsmv1998.eurodiffusion.data.CountryData;
import com.gmail.maxsmv1998.eurodiffusion.model.EuroDiffusionModel;
import com.gmail.maxsmv1998.eurodiffusion.util.InputReader;

import java.util.List;

public class Runner {
    public static void main(String[] args) {
        List<CountryData> countries = InputReader.getInputData("case1.txt");
        var a = new EuroDiffusionModel(countries);
    }

}
