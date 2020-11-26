package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetUserBitrixRestRequest implements BitrixRestRequest {

    public GetUserBitrixRestRequest(Long id) {
        this.id = id;
    }

    private Long id;
}
