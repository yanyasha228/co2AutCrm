package com.co2AutomaticCrm.Models.BitrixModels;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class BitrixUser {

    private long id;

    private String email;

    private String name;

    private String lastName;

    private String secondName;

    private Set<Integer> bitrixDepartmentsIds;

}
