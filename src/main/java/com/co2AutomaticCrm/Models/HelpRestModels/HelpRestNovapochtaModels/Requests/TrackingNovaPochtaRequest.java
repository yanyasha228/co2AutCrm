package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestNovapochtaModels.Requests;

import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestNovapochtaModels.HelpDto.TrackingMethodPropertiesDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrackingNovaPochtaRequest {

    private String modelName = "TrackingDocument";

    private String calledMethod = "getStatusDocuments";

    @JsonProperty(value = "methodProperties")
    private TrackingMethodPropertiesDto methodProperties;

}

