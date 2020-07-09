package com.online.coursedetails.utils;

import java.util.HashMap;
import java.util.Map;

public enum CountryCode {

    INDIA("IND"),
    USA("USA"),
    GERMANY("DEU");

    private String value;
    private static Map<String, String> lookup = new HashMap<>();

    static {
        for (CountryCode countryCode : CountryCode.values()) {
            lookup.put(countryCode.value, countryCode.name());
        }
    }

    CountryCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static String getName(String value) {
        return lookup.get(value);
    }

    public static Map<String, String> getCountryCodes()
    {
        return lookup;
    }
}
