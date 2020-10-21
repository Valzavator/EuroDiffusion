package com.gmail.maxsmv1998.eurodiffusion.data;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CountryData {
    String name;
    int xl;
    int yl;
    int xh;
    int yh;
}
