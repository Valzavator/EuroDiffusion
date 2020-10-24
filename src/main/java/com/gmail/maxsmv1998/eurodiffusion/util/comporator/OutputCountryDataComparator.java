package com.gmail.maxsmv1998.eurodiffusion.util.comporator;

import com.gmail.maxsmv1998.eurodiffusion.data.OutputCountryData;

import java.util.Comparator;

public class OutputCountryDataComparator implements Comparator<OutputCountryData> {

    @Override
    public int compare(OutputCountryData o1, OutputCountryData o2) {
        int amountOfDaysCompared = Integer.compare(o1.getAmountOfDays(), o2.getAmountOfDays());
        return amountOfDaysCompared == 0
                ? o1.getName().compareTo(o2.getName())
                : amountOfDaysCompared;
    }
}
