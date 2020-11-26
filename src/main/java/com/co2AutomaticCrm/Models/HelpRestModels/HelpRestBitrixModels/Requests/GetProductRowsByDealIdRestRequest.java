package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetProductRowsByDealIdRestRequest implements BitrixRestRequest {

    public GetProductRowsByDealIdRestRequest(Long dealId) {
        this.dealId = dealId;
    }

    @JsonProperty(value = "id")
    private Long dealId;

}
