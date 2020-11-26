package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;


import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ContactRestBitrixDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddContactBitrixRestRequest implements BitrixRestRequest {

    public AddContactBitrixRestRequest(ContactRestBitrixDto contactRestBitrixDto) {

        this.contactRestBitrixDto = contactRestBitrixDto;
    }

    @JsonProperty(value = "fields")
    private ContactRestBitrixDto contactRestBitrixDto;

}
