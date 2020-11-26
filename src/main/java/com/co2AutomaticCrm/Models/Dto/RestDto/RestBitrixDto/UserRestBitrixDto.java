package com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UserRestBitrixDto {

    @JsonProperty(value = "ID")
    private int id;

    @JsonProperty(value = "EMAIL")
    private String email;

    @JsonProperty(value = "NAME")
    private String name;

    @JsonProperty(value = "LAST_NAME")
    private String lastName;

    @JsonProperty(value = "SECOND_NAME")
    private String secondName;

    @JsonProperty(value = "UF_DEPARTMENT")
    private Set<Integer> bitrixDepartmentsIds;

}
