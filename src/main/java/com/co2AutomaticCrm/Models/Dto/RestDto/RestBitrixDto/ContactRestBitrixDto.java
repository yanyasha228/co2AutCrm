package com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ContactRestBitrixDto {

    @JsonProperty(value = "ID")
    private int id;

    @JsonProperty(value = "NAME")
    private String name;

    @JsonProperty(value = "LAST_NAME")
    private String lastName;

    @JsonProperty(value = "SECOND_NAME")
    private String secondName = "";

    private String phoneNumber;

    @JsonProperty(value = "UF_CRM_1576590003429", access = JsonProperty.Access.WRITE_ONLY)
    private String isWholesaleFlag;

    @JsonProperty(value = "PHONE")
    public void setPhone(List<Map<String, String>> custProp) {
        if (custProp != null) {
            phoneNumber = custProp.get(0).get("VALUE");
        }
    }

    @JsonProperty(value = "PHONE")
    public List<Map<String, String>> getPhone() {

        List<Map<String, String>> phoneParams = new ArrayList<>();

        Map<String, String> phoneValueParam = new HashMap<>();

        phoneValueParam.put("VALUE", this.phoneNumber);

        phoneParams.add(phoneValueParam);

        return phoneParams;
    }

}
