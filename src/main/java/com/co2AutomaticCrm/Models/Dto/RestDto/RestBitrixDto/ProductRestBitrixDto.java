package com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto;

import com.co2AutomaticCrm.Models.ModelEnums.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class ProductRestBitrixDto {

    @JsonProperty(value = "ID")
    private long bitrixId;

    @JsonProperty(value = "NAME")
    private String name;

    @JsonProperty(value = "ACTIVE", access = JsonProperty.Access.WRITE_ONLY)
    private String active;

    private String main_image;

    @JsonProperty(value = "PRICE")
    private float price;

    private float wholeSalePrice;

    private long id;

    private int amount;

    @JsonProperty(value = "CURRENCY_ID")
    private Currency currency;

    @JsonProperty(value = "PREVIEW_PICTURE")
    public void setPreviewPictureJson(Map<String, String> custProp) {
        if (custProp != null) {
            main_image = custProp.get("showUrl");
        } else main_image = "";
    }


    @JsonProperty(value = "PROPERTY_106")
    public void setWholeSalePriceJson(Map<String, String> custProp) {
        if (custProp != null) {
            wholeSalePrice = Float.parseFloat(custProp.get("value"));
        } else wholeSalePrice = 0;
    }

    @JsonProperty(value = "PROPERTY_106")
    public float getWholeSalePrice() {
        return wholeSalePrice;
    }

    @JsonProperty(value = "PROPERTY_104")
    public void setIdJson(Map<String, String> custProp) {
        if (custProp != null) {
            id = Integer.parseInt(custProp.get("value"));
        } else id = 0;
    }

    @JsonProperty(value = "PROPERTY_104")
    public long getId() {
        return id;
    }

    @JsonProperty(value = "PROPERTY_102")
    public void setAmountJson(Map<String, String> custProp) {
        if (custProp != null) {
            amount = Integer.parseInt(custProp.get("value"));
        } else amount = 0;
    }

    @JsonProperty(value = "PROPERTY_102")
    public int getAmount() {
        return amount;
    }


}
