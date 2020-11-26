package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import lombok.Data;

import java.util.HashMap;

@Data
public class GetUserByEmailRestRequest implements BitrixRestRequest {

    public GetUserByEmailRestRequest(String email) {

        this.filter.put("email", email);

    }

    private HashMap<String, String> filter = new HashMap<>();

}
