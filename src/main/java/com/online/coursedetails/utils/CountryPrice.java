package com.online.coursedetails.utils;

import java.util.HashMap;
import java.util.Map;

public enum CountryPrice {

    INDIA(0.0),
    USA(74.84),
    GERMANY(84.43);

    private Double value;
    private static Map<Double, String> lookup = new HashMap<>();

    static {
        for (CountryPrice countryPrice : CountryPrice.values()) {
            lookup.put(countryPrice.value, countryPrice.name());
        }
    }

    CountryPrice(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return this.value;
    }

    public static String getName(String value) {
        return lookup.get(value);
    }

    public static Map<Double, String> getCountryCodes()
    {
        return lookup;
    }

}
