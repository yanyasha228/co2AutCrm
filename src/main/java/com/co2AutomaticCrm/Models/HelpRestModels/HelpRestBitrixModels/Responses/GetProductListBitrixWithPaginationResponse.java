package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRestBitrixDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetProductListBitrixWithPaginationResponse implements BitrixRestResponse {

    @JsonProperty(value = "result")
    private List<ProductRestBitrixDto> productDtos;

    @JsonProperty(value = "next")
    private int next;

}
