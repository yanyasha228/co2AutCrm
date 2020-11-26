package com.co2AutomaticCrm.Models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "manager_daily_salaries")
public class ManagerDailySalary extends DailySalary {

    public ManagerDailySalary() {
        super();
    }

}
