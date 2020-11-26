package com.co2AutomaticCrm.Models.ModelEnums;

public enum SupplyStatus {

    ADMISSION, DELIVERY_RETURN, RETURN, WORKSHOP;

    public String getAlias() {

        switch (name()) {

            case "ADMISSION":
                return "Поставка";

            case "DELIVERY_RETURN":
                return "Возврат доставки";

            case "RETURN":
                return "Возврат";

            case "WORKSHOP":
                return "Мастерская";

        }

        return "";
    }
}
