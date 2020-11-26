package com.co2AutomaticCrm.Models.BitrixModels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BitrixProductRow {

    private long id;

    private long productBitrixId;

    private String productName;

    private float price;

    private int quantity;

    private float discount;

    private float sumPrice;

    private float sumDiscount;

}
