package com.co2AutomaticCrm.Models;

import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import com.co2AutomaticCrm.Models.ModelEnums.SupplyStatus;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@DiscriminatorValue("S")
public class Supply extends ProductManipulation {

    public Supply() {
        this.setType(ProductManipulationType.SUPPLY);
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dependent_demand_id", referencedColumnName = "id")
    private SupplyDependentDemand supplyDependentDemand;

    @Column(name = "supply_status")
    @Enumerated(EnumType.STRING)
    private SupplyStatus supplyStatus;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "supply_provider_id", referencedColumnName = "id")
    private SupplyProvider supplyProvider;


}
