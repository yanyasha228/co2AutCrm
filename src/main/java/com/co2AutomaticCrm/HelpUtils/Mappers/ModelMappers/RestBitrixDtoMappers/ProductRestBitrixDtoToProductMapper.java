package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.RestBitrixDtoMappers;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRestBitrixDto;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Services.ProductService;
import com.co2AutomaticCrm.Validators.PriceCurrencyBitrixDtoValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ProductRestBitrixDtoToProductMapper implements Mapper<Product, ProductRestBitrixDto> {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PriceCurrencyBitrixDtoValidator priceCurrencyBitrixDtoValidator;

    @Autowired
    private ProductService productService;

    public Product toEntity(ProductRestBitrixDto productDto) {

        if (productDto == null) return null;

        Product product = mapper.map(productDto, Product.class);

        Optional<Product> oldProductOpt = productService.findById(productDto.getId());

        if (oldProductOpt.isPresent()) {

            Product oldProduct = oldProductOpt.get();

            product.setMain_image(oldProduct.getMain_image());
            product.setName(oldProduct.getName());
            product.increaseAmount(oldProduct.getAmount());
            product.setCurrency(oldProduct.getCurrency());
            product.setPrice(oldProduct.getPrice());
            product.setWholeSalePrice(oldProduct.getWholeSalePrice());
//            product.setDependent_Products(oldProduct.getDependent_Products());
            product.setGroup(oldProduct.getGroup());
            product.setCreationDate(oldProduct.getCreationDate());
            product.setLastUpdatingDate(oldProduct.getLastUpdatingDate());
            product.setAvailability(oldProduct.isAvailability());

        }

        return product;

    }


    public ProductRestBitrixDto toDto(Product product) {

        if (Objects.isNull(product)) return null;

        ProductRestBitrixDto productRestBitrixDto = priceCurrencyBitrixDtoValidator
                .convertProductDtoCurrencyToUAH(mapper.map(product, ProductRestBitrixDto.class));

        if (product.isAvailability()) {
            productRestBitrixDto.setActive("Y");
        } else productRestBitrixDto.setActive("N");

        return productRestBitrixDto;
    }
}
