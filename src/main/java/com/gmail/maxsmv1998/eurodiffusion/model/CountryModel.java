package com.gmail.maxsmv1998.eurodiffusion.model;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

import java.util.ArrayList;
import java.util.List;


@Value
public class CountryModel {
    String name;
    int xl;
    int yl;
    int xh;
    int yh;

    @NonFinal
    List<CityModel> cities = new ArrayList<>();
    @NonFinal
    boolean isComplete = false;
    @NonFinal
    int amountOfDays = 0;

    @Builder
    public CountryModel(String name, int xl, int yl, int xh, int yh, List<CityModel> cities) {
        this.name = name;
        this.xl = xl;
        this.yl = yl;
        this.xh = xh;
        this.yh = yh;
    }

    public boolean incAmountOfDays() {
        if (!isComplete) {
            amountOfDays++;
        }
        return isComplete;
    }

    public boolean checkComplete() {
        isComplete = true;

        return isComplete;
    }

    public void addCity(CityModel city) {
        cities.add(city);
    }
}
