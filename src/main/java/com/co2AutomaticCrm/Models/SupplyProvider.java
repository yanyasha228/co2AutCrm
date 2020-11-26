package com.co2AutomaticCrm.Models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "supply_providers")
public class SupplyProvider {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String name;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm")
    private LocalDateTime creationDate;


}
