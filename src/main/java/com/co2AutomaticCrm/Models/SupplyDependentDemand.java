package com.co2AutomaticCrm.Models;

import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("SDD")
public class SupplyDependentDemand extends ProductManipulation {

    public SupplyDependentDemand() {
        this.setType(ProductManipulationType.DEMAND);
    }

    @OneToOne
    @JoinColumn(name = "dependent_supply_id", referencedColumnName = "id")
    private Supply supply;

}
