package com.co2AutomaticCrm.Models.BitrixModels;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BitrixNotify {


    public BitrixNotify(BitrixUser bitrixUser, String message) {
        this.manager = bitrixUser;
        this.message = message;
    }

    private BitrixUser manager;

    private String message;


}
