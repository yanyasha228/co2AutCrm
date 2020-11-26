package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses;


import com.co2AutomaticCrm.HelpUtils.DeserializeUtils.NumericBooleanDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostNotifyBitrixRestResponse implements BitrixRestResponse {

    @JsonDeserialize(using = NumericBooleanDeserializer.class)
    private boolean result;

}
