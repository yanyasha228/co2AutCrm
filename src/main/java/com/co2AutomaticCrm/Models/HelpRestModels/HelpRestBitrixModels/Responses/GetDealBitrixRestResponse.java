package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.DealRestBitrixDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetDealBitrixRestResponse implements BitrixRestResponse {

    @JsonProperty(value = "result")
    private DealRestBitrixDto dealRestBitrixDto;

}
