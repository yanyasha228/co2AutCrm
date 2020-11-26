package com.co2AutomaticCrm.Models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "salary_decreases")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class SalaryDecrease {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "salary_decrease_amount", updatable = false)
    private float salaryDecreaseAmount;

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
