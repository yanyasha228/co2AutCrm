package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.RestPromDtoMappers;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestPromDto.ProductRestPromDto;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ProductRestPromDtoMapper implements Mapper<Product, ProductRestPromDto> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductService productService;


    public Product toEntity(ProductRestPromDto productDto) {
        if (productDto == null) return null;
        Product product = mapper.map(productDto, Product.class);
        Optional<Product> oldProductOpt = productService.findById(productDto.getId());
        if (oldProductOpt.isPresent()) {

            Product oldProduct = oldProductOpt.get();

            product.setGroup(oldProduct.getGroup());
            product.setBitrixId(oldProduct.getBitrixId());
            product.increaseAmount(oldProduct.getAmount());
            product.setWholeSalePrice(oldProduct.getWholeSalePrice());
//            product.setDependent_Products(oldProduct.getDependent_Products());
            product.setCreationDate(oldProduct.getCreationDate());
            product.setLastUpdatingDate(oldProduct.getLastUpdatingDate());
            product.setAvailability(oldProduct.isAvailability());

        }

        return product;

    }


    public ProductRestPromDto toDto(Product product) {

        return Objects.isNull(product) ? null : mapper.map(product, ProductRestPromDto.class);

    }

}
