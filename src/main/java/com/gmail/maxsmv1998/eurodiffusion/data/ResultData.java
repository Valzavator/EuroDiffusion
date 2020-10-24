package com.gmail.maxsmv1998.eurodiffusion.data;

import lombok.Value;

import java.util.List;
import java.util.StringJoiner;

@Value
public class ResultData {
    List<OutputCountryData> countries;

    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("\n");
        countries.forEach(country -> sj.add(country.getName() + " " + country.getAmountOfDays()));
        return sj.toString();
    }

}
