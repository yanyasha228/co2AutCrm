package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;


import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRestBitrixDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostProductBitrixRestRequest implements BitrixRestRequest {

    public PostProductBitrixRestRequest(ProductRestBitrixDto product) {

        this.product = product;

    }

    @JsonProperty(value = "fields")
    private ProductRestBitrixDto product;


}
