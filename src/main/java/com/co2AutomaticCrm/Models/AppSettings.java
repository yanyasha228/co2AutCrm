package com.co2AutomaticCrm.Models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "admin_settings")
public class AppSettings {

    @Id
    @Setter(value = AccessLevel.NONE)
    private int id = 1;

    @Column(name = "USD_CURRENCY")
    private float usdCurrency;

    @Column(name = "EUR_CURRENCY")
    private float eurCurrency;


}