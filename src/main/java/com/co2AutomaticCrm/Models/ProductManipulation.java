package com.co2AutomaticCrm.Models;

import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity(name = "product_manipulations")
@DiscriminatorColumn(name = "disc")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ProductManipulation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "type")
    private ProductManipulationType type;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "manipulation_id")
    private List<ProductLine> productLines = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    @DateTimeFormat(pattern = "dd-M-yyyy hh:mm")
    private LocalDateTime creationDate;

//    @Column(name = "url_detailed")
//    private String urlDetailed;

    @Column
    private String description;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
