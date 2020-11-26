package com.co2AutomaticCrm.Models.ModelEnums;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum Currency {

    UAH, USD, EUR, RUB;


    private static Map<String, Currency> namesMap = new HashMap<>();

    static {
        namesMap.put("UAH", UAH);
        namesMap.put("USD", USD);
        namesMap.put("EUR", EUR);
        namesMap.put("RUB", RUB);
    }

    @JsonCreator
    public static Currency forValue(String value) {
        return namesMap.get(value);
    }

    @JsonValue
    public String toValue() {
        for (Map.Entry<String, Currency> entry : namesMap.entrySet()) {
            if (entry.getValue() == this)
                return entry.getKey();
        }

        return null; // or fail
    }


    public String getCurrencySymbol() {
        String curSymb = "";
        switch (name()) {
            case "UAH":
                curSymb = "₴";
                break;
            case "USD":
                curSymb = "$";
                break;
            case "EUR":
                curSymb = "€";
                break;
        }
        return curSymb;
    }

}
