package com.co2AutomaticCrm.Models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "daily_salaries")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DailySalary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "worker_id", referencedColumnName = "id", updatable = false, nullable = false)
    private Worker worker;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dailySalary")
    private List<SalaryIncrease> salaryIncreases;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
//    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm")
    private LocalDate creationDate;


    @NotNull
    @Column(name = "sum_daily_salary_amount", updatable = false, nullable = false)
    private float sumDailySalaryAmount;


    @CreationTimestamp
    @Column(name = "detailed_creation_date", updatable = false)
//    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm")
    private LocalDateTime detailedCreationDate;


}
