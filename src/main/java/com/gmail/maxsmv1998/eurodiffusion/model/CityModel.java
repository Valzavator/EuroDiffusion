package com.gmail.maxsmv1998.eurodiffusion.model;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Builder
@Value
public class CityModel {
    @ToString.Exclude
    CountryModel country;
    BankModel bankModel;
    int x;
    int y;
}
