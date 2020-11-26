package com.co2AutomaticCrm.Dao;

import com.co2AutomaticCrm.Models.ManagerSalaryIncrease;

import java.util.Optional;

public interface ManagerSalaryIncreaseDao extends SalaryIncreaseDao<ManagerSalaryIncrease> {

    Optional<ManagerSalaryIncrease> findByBitrixDealId(Long id);

}
