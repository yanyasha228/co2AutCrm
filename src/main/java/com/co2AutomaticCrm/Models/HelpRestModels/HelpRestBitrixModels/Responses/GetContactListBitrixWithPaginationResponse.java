package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ContactRestBitrixDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetContactListBitrixWithPaginationResponse implements BitrixRestResponse {

    @JsonProperty(value = "result")
    private List<ContactRestBitrixDto> contactDtos;

    @JsonProperty(value = "next")
    private int next;
}
