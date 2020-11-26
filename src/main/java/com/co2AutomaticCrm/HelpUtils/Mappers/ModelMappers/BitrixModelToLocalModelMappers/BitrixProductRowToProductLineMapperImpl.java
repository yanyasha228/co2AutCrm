package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.BitrixModelToLocalModelMappers;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Models.ProductLine;
import com.co2AutomaticCrm.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class BitrixProductRowToProductLineMapperImpl implements BitrixProductRowToProductLineMapper {

    @Autowired
    private ProductService productService;

    @Override
    public ProductLine toProductLine(BitrixProductRow dto) throws EntityInconsistencyException {
        if (Objects.isNull(dto)) return null;

        Optional<Product> productOpt = productService.findByBitrixId(dto.getProductBitrixId());

        if (!productOpt.isPresent())
            throw new EntityInconsistencyException("No such product : " + dto.getProductName());
        ProductLine productLine = new ProductLine();
        productLine.setAmount(dto.getQuantity());
        productLine.setManipulationMomentAmount(productOpt.get().getAmount());
        productLine.setProduct(productOpt.get());

        return productLine;

    }

    @Override
    public BitrixProductRow toBitrixProductRow(ProductLine entity) {
        if (Objects.isNull(entity)) return null;
        BitrixProductRow bitrixProductRow = new BitrixProductRow();
        bitrixProductRow.setPrice(entity.getProduct().getPrice());
        bitrixProductRow.setProductBitrixId(entity.getProduct().getBitrixId());
        bitrixProductRow.setQuantity(entity.getAmount());
        bitrixProductRow.setProductName(entity.getProduct().getName());
        return bitrixProductRow;
    }

}
