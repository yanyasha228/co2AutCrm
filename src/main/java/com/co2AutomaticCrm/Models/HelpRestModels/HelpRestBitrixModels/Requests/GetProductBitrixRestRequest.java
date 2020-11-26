package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetProductBitrixRestRequest implements BitrixRestRequest {

    public GetProductBitrixRestRequest(long id) {
        this.id = id;
    }

    private long id;

}
