package com.co2AutomaticCrm.Dao;

import com.co2AutomaticCrm.Models.BitrixDealDemand;

import java.util.Optional;

public interface BitrixDealDemandDao extends ProductManipulationDao<BitrixDealDemand> {

    Optional<BitrixDealDemand> findByBitrixDealId(long id);

}
