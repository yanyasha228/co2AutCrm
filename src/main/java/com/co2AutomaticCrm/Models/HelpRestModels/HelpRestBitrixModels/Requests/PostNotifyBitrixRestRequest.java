package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixNotify;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostNotifyBitrixRestRequest implements BitrixRestRequest {


    public PostNotifyBitrixRestRequest(BitrixNotify bitrixNotify) {

        this.managerId = bitrixNotify.getManager().getId();
        this.message = bitrixNotify.getMessage();

    }

    @JsonProperty(value = "to")
    private long managerId;

    @JsonProperty(value = "message")
    private String message;

}
