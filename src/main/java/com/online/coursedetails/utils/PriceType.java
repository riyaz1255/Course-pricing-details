package com.online.coursedetails.utils;

import java.util.HashMap;
import java.util.Map;

public enum PriceType {

    FREE("0"),
    ONE_TIME_PAYMENT("1"),
    SUBSCRIPTION("2");

    private String value;
    private static Map<String, String> lookup = new HashMap<>();

    static {
        for (PriceType priceType : PriceType.values()) {
            lookup.put(priceType.value, priceType.name());
        }
    }

    PriceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public static String getName(String value) {
        return lookup.get(value);
    }
}
