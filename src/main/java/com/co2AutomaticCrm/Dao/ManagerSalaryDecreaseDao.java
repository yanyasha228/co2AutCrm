package com.co2AutomaticCrm.Dao;

import com.co2AutomaticCrm.Models.ManagerSalaryDecrease;

import java.util.Optional;

public interface ManagerSalaryDecreaseDao extends SalaryDecreaseDao<ManagerSalaryDecrease> {

    Optional<ManagerSalaryDecrease> findByBitrixDealId(Long id);

}
