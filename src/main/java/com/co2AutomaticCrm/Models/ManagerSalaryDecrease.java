package com.co2AutomaticCrm.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "manager_salary_decreases")
public class ManagerSalaryDecrease extends SalaryDecrease {

    public ManagerSalaryDecrease() {
        super();
    }

    @Column(name = "bitrix_deal_id", updatable = false)
    private long bitrixDealId;

}
