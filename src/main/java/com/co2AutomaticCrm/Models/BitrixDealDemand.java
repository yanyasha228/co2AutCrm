package com.co2AutomaticCrm.Models;

import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("BDD")
public class BitrixDealDemand extends ProductManipulation {

    public BitrixDealDemand() {
        this.setType(ProductManipulationType.DEMAND);
    }

    @Column(name = "bitrix_deal_id", unique = true, nullable = false)
    private long bitrixDealId;

}
