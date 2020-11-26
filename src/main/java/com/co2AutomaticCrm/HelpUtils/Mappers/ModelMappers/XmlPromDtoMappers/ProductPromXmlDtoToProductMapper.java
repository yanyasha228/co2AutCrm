package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.XmlPromDtoMappers;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.ProductXmlPromDto;
import com.co2AutomaticCrm.Models.Group;
import com.co2AutomaticCrm.Models.ModelEnums.Currency;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Services.GroupService;
import com.co2AutomaticCrm.Services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
public class ProductPromXmlDtoToProductMapper implements Mapper<Product, ProductXmlPromDto> {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private GroupService groupService;

    public Product toEntity(ProductXmlPromDto productDto) {

        if (productDto == null) return null;
        Product product = mapper.map(productDto, Product.class);

        Optional<Product> oldProductOpt = productService.findById(productDto.getId());
        if (oldProductOpt.isPresent()) {
            Product oldProduct = oldProductOpt.get();
            product.setBitrixId(oldProduct.getBitrixId());
            product.setName(oldProduct.getName());
            product.setMain_image(productDto.getPictures().get(0));
            product.increaseAmount(oldProduct.getAmount());
            product.setCurrency(oldProduct.getCurrency());
            product.setPrice(oldProduct.getPrice());
            product.setWholeSalePrice(oldProduct.getWholeSalePrice());
//            product.setDependent_Products(oldProduct.getDependent_Products());
            product.setGroup(groupService.findById(productDto.getCategoryId()).get());
            product.setCreationDate(oldProduct.getCreationDate());
            product.setLastUpdatingDate(oldProduct.getLastUpdatingDate());
            product.setAvailability(oldProduct.isAvailability());


        }else {
            product.setName(productDto.getName());
            product.setMain_image(productDto.getPictures().get(0));
            product.setCurrency(Currency.valueOf(productDto.getCurrency()));
            product.setPrice(productDto.getPrice());
//            product.setDependent_Products(oldProduct.getDependent_Products());
            product.setGroup(groupService.findById(productDto.getCategoryId()).get());
            product.setCreationDate(LocalDateTime.now());
            product.setLastUpdatingDate(LocalDateTime.now());
            product.setAvailability(false);
        }
        return product;

    }


    public ProductXmlPromDto toDto(Product product) {
        return Objects.isNull(product) ? null : mapper.map(product, ProductXmlPromDto.class);
    }

}
