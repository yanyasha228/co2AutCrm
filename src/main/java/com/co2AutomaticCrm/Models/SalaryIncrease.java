package com.co2AutomaticCrm.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "salary_increases")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SalaryIncrease {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "salary_increase_ammount", updatable = false)
    private float salaryIncreaseAmount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "daily_salary_id")
    private DailySalary dailySalary;

    @NotNull
    @OneToOne
    @JoinColumn(name = "worker_id", referencedColumnName = "id", updatable = false, nullable = false)
    private Worker worker;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
//    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm")
    private LocalDate creationDate;

    @CreationTimestamp
    @Column(name = "detailed_creation_date", updatable = false)
//    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm")
    private LocalDateTime detailedCreationDate;


}
