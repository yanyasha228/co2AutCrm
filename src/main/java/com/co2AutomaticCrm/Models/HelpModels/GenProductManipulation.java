package com.co2AutomaticCrm.Models.HelpModels;

import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationDisc;
import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import com.co2AutomaticCrm.Models.ModelEnums.SupplyStatus;
import com.co2AutomaticCrm.Models.*;
import lombok.Data;
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
@Entity
@Table(name = "product_manipulations")
public class GenProductManipulation {

    @Id
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "disc")
    private ProductManipulationDisc disc;

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

    @Column
    private String description;

    @Column(name = "bitrix_deal_id", unique = true, nullable = false)
    private Long bitrixDealId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dependent_demand_id", referencedColumnName = "id")
    private SupplyDependentDemand supplyDependentDemand;

    @Column(name = "supply_status")
    @Enumerated(EnumType.STRING)
    private SupplyStatus supplyStatus;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "supply_provider_id", referencedColumnName = "id")
    private SupplyProvider supplyProvider;

    @OneToOne
    @JoinColumn(name = "dependent_supply_id", referencedColumnName = "id")
    private Supply supply;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String urlDetailed;

    private String manipulationResultStringByProduct;

    private int manipulationMomentAmount;

    @PostLoad
    private void genUrl() {
        switch (disc) {
            case BDD:
                urlDetailed = String.format("https://co2.bitrix24.ua/crm/deal/details/%s/", bitrixDealId);
                break;
            case S:
                urlDetailed = String.format("/supplies/%s/show", id);
                break;
            case SDD:
                urlDetailed = String.format("/supplies/%s/show", supply.getId());
                break;
            case C:
                urlDetailed = String.format("/corrections/%s/show", id);
                break;
        }

    }


}
