package com.co2AutomaticCrm.Models.NovaPochtaModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NovaPochtaTrackingDocument {

    public NovaPochtaTrackingDocument(String documentNumber, String phoneNumber) {
        this.clientDocumentNumber = documentNumber;
        this.clientPhoneNumber = phoneNumber;
    }

    @JsonProperty(value = "DocumentNumber")
    private String clientDocumentNumber;

    @JsonProperty(value = "Phone")
    private String clientPhoneNumber;
}