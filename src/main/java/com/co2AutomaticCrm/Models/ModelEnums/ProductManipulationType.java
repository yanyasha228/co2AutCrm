package com.co2AutomaticCrm.Models.ModelEnums;

public enum ProductManipulationType {
    SUPPLY, DEMAND;

    public String getAlias() {

        switch (name()) {

            case "SUPPLY":
                return "Поставка";

            case "DEMAND":
                return "Уход";

        }

        return "";
    }
}
