package com.gmail.maxsmv1998.eurodiffusion.util.comparator;

import com.gmail.maxsmv1998.eurodiffusion.data.OutputCountryData;

import java.util.Comparator;

public class OutputCountryDataComparator implements Comparator<OutputCountryData> {

    @Override
    public int compare(OutputCountryData o1, OutputCountryData o2) {
        return o1.getAmountOfDays() == o2.getAmountOfDays()
                ? o1.getName().compareTo(o2.getName())
                : Integer.compare(o1.getAmountOfDays(), o2.getAmountOfDays());
    }
}
