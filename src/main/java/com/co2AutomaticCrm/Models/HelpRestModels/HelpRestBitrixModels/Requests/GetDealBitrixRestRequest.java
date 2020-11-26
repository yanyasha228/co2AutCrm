package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetDealBitrixRestRequest implements BitrixRestRequest {

    public GetDealBitrixRestRequest(int id) {
        this.id = id;
    }

    private int id;

}
