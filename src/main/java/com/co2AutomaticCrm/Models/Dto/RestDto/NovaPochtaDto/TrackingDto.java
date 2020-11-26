package com.co2AutomaticCrm.Models.Dto.RestDto.NovaPochtaDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TrackingDto {

    @JsonProperty(value = "RecipientAddress")
    private String deliveryPlaceName;

}
