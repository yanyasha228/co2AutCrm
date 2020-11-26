package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetContactBitrixRestRequest implements BitrixRestRequest {

    public GetContactBitrixRestRequest(int id) {
        this.id = id;
    }

    private int id;
}
