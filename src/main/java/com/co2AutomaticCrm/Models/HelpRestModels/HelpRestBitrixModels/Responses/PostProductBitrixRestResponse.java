package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostProductBitrixRestResponse implements BitrixRestResponse {

    @JsonProperty(value = "result")
    private int addedProductId;

}
