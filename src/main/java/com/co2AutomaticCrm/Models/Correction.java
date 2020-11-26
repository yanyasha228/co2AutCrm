package com.co2AutomaticCrm.Models;

import com.co2AutomaticCrm.Models.ModelEnums.CorrectionType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("C")
public class Correction extends ProductManipulation {

    public Correction() {
    }

    @Column(name = "correction_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private CorrectionType correctionType;

}
