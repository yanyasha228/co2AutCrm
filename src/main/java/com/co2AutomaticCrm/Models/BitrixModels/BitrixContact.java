package com.co2AutomaticCrm.Models.BitrixModels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BitrixContact {

    private int id;

    private String name;

    private String secondName;

    private String lastName;

    private String phoneNumber;

    private boolean isWholesaleFlag;

}
