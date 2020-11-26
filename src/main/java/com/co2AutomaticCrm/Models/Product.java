package com.co2AutomaticCrm.Models;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleAmountDecreasingException;
import com.co2AutomaticCrm.Models.ModelEnums.Currency;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

////CLASS_FIELDS_AMOUNT = 12
@Data
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Id
    private long id;

    @Column(name = "crm_id", unique = true)
    private long bitrixId;

    @Column(name = "name")
    private String name;

    @Column(name = "main_image_url")
    private String main_image;

    @Column(name = "quantity")
    @Min(0)
    @Setter(value = AccessLevel.NONE)
    private int amount;

    @Column(name = "moneyCurrency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "price")
    private float price;

    @Column(name = "wholesale_price")
    private float wholeSalePrice;

//    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
//    @JoinColumn(name = "dependent_product_ids")
//    private List<Product> dependent_Products = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Group group;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm")
    private LocalDateTime creationDate;

    @Column(name = "last_updating_date")
    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm")
    private LocalDateTime lastUpdatingDate;

    @Column(name = "availability")
    @Type(type = "true_false")
    private boolean availability;

    public void increaseAmount(int quantity) {
        this.amount += quantity;
    }

    public void decreaseAmount(int quantity) throws ImpossibleAmountDecreasingException {

        if (this.amount - quantity < 0) {
            throw new ImpossibleAmountDecreasingException("Insufficient amount of product");
        } else {
            this.amount -= quantity;
        }
    }

    //    @ManyToMany(mappedBy = "products")
//    private List<Order> ordersList;
    @PreUpdate
    @PrePersist
    protected void prePersistUpdateFunc() {
        availability = amount > 0;
        lastUpdatingDate = LocalDateTime.now();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


}

