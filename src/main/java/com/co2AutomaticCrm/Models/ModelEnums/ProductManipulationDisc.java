package com.co2AutomaticCrm.Models.ModelEnums;

public enum ProductManipulationDisc {
    SDD, BDD, S, C;

    public String getAlias() {

        switch (name()) {


            case "BDD":
                return "Сделка";

            case "S":
                return "Поставка";

            case "SDD":
                return "Зависимый уход";

            case "C":
                return "Коррекция";
        }

        return "";
    }
}
