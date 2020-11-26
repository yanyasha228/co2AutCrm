package com.co2AutomaticCrm.Dao;

import com.co2AutomaticCrm.Models.SupplyDependentDemand;

import java.util.Optional;

public interface SupplyDependentDemandDao extends ProductManipulationDao<SupplyDependentDemand> {

    Optional<SupplyDependentDemand> findBySupplyId(Long id);

}
