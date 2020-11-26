package com.co2AutomaticCrm.Models.HelpModels;

import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import com.co2AutomaticCrm.Models.ProductLine;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductManipulationLine {

    public ProductManipulationLine(ProductManipulationType productManipulationType, ProductLine productLine){
        this.productLine = productLine;
        this.productManipulationType = productManipulationType;
    }

    private ProductManipulationType productManipulationType;

    private ProductLine productLine;
}
