package com.co2AutomaticCrm.Models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product_lines")
public class ProductLine {

    public ProductLine(Product product , Integer amount){
        this.product = product;
        this.amount = amount;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @Column(name = "product_amount")
    private int amount;

    @Column(name="manipulation_moment_amount")
    private int manipulationMomentAmount;

}
