package com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRowRestBitrixDto {

    @JsonProperty(value = "ID", access = JsonProperty.Access.WRITE_ONLY)
    private int id;

    @JsonProperty(value = "PRODUCT_ID")
    private int productBitrixId;

    @JsonProperty(value = "PRODUCT_NAME", access = JsonProperty.Access.WRITE_ONLY)
    private String productName;

    @JsonProperty(value = "PRICE")
    private float price;

    @JsonProperty(value = "QUANTITY")
    private int quantity;

    @JsonProperty(value = "DISCOUNT_SUM")
    private float discount;


}
