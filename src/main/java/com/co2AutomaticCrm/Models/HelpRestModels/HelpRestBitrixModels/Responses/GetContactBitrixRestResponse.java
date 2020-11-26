package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ContactRestBitrixDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetContactBitrixRestResponse implements BitrixRestResponse {

    @JsonProperty(value = "result")
    private ContactRestBitrixDto contactRestBitrixDto;

}
