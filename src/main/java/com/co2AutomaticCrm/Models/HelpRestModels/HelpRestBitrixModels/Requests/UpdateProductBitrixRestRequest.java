package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRestBitrixDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateProductBitrixRestRequest implements BitrixRestRequest {

    public UpdateProductBitrixRestRequest(ProductRestBitrixDto productDto) {

        this.productDto = productDto;
        this.bitrixId = productDto.getBitrixId();

    }

    @JsonProperty(value = "id")
    private Long bitrixId;

    @JsonProperty(value = "fields")
    private ProductRestBitrixDto productDto;

}
