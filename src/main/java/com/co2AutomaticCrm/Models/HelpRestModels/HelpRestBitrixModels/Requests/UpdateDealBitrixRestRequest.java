package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.DealRestBitrixDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateDealBitrixRestRequest implements BitrixRestRequest {

    public UpdateDealBitrixRestRequest(DealRestBitrixDto dealRestBitrixDto) {

        this.bitrixId = dealRestBitrixDto.getId();
        this.dealRestBitrixDto = dealRestBitrixDto;
    }

    @JsonProperty(value = "id")
    private long bitrixId;

    @JsonProperty(value = "fields")
    private DealRestBitrixDto dealRestBitrixDto;

}
