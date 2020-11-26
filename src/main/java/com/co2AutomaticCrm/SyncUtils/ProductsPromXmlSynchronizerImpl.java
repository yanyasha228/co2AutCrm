package com.co2AutomaticCrm.SyncUtils;


import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.XmlPromDtoMappers.ProductPromXmlDtoToProductMapper;
import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.CatalogXmlPromDto;
import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.CategoryPromXmlDto;
import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.ProductXmlPromDto;
import com.co2AutomaticCrm.Models.Group;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.RestServices.PromRestServices.XmlServices.ProductCatalogXmlService;
import com.co2AutomaticCrm.Services.GroupService;
import com.co2AutomaticCrm.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ProductsPromXmlSynchronizerImpl implements ProductsPromXmlSynchronizer {

    @Autowired
    private ProductPromXmlDtoToProductMapper productPromXmlDtoToProductMapper;

    @Autowired
    private ProductService productService;

    @Autowired
    private GroupService groupService;


    @Autowired
    private ProductCatalogXmlService productCatalogXmlService;


    public List<Product> syncProductsWithPromXml(String syncUrl) {

        CatalogXmlPromDto catalogPromDto = productCatalogXmlService.getCatalog(syncUrl).orElse(null);

        List<Group> groups = new ArrayList<>();
        for (CategoryPromXmlDto catPr : getMainCategories(catalogPromDto)) {
            Group nGroup = new Group();
            nGroup.setId(catPr.getId());
            nGroup.setName(catPr.getName());
            groups.add(nGroup);
        }
        groupService.save(groups);

        setOnlyMainCategoriesInProducts(catalogPromDto);

        List<Product> productList = catalogPromDto.getShopXmlPromDto().getOffersList().stream()
                .map(productXmlPromDto -> productPromXmlDtoToProductMapper.toEntity(productXmlPromDto))
                .collect(Collectors.toList());


        return productService.save(productList);

    }

    private void setOnlyMainCategoriesInProducts(CatalogXmlPromDto catalogXmlPromDto) {

        List<CategoryPromXmlDto> mainCats = getMainCategories(catalogXmlPromDto);

        Map<Integer, CategoryPromXmlDto> mainCategoriesMap = new HashMap<>();

        Map<Integer, CategoryPromXmlDto> categoriesMap = new HashMap<>();

        for (CategoryPromXmlDto mCat : mainCats) {
            mainCategoriesMap.put(mCat.getId(), mCat);
        }

        for (CategoryPromXmlDto cat : catalogXmlPromDto.getShopXmlPromDto().getCategories()) {
            if (cat.getParentId() != 0) {
                categoriesMap.put(cat.getId(), mainCategoriesMap.get(cat.getParentId()));
            } else {
                categoriesMap.put(cat.getId(), cat);
            }
        }

        for (ProductXmlPromDto prod : catalogXmlPromDto.getShopXmlPromDto().getOffersList()) {
            prod.setCategoryId(categoriesMap.get(prod.getCategoryId()).getId());
        }


    }

    private List<CategoryPromXmlDto> getMainCategories(CatalogXmlPromDto catalogXmlPromDto) {

        return catalogXmlPromDto.getShopXmlPromDto().getCategories()
                .stream()
                .filter(categoryPromXmlDto -> categoryPromXmlDto.getParentId() == 0)
                .collect(Collectors.toList());
    }

    private List<CategoryPromXmlDto> getParentCategories(CatalogXmlPromDto catalogXmlPromDto) {

        return catalogXmlPromDto.getShopXmlPromDto().getCategories()
                .stream()
                .filter(categoryPromXmlDto -> categoryPromXmlDto.getParentId() != 0)
                .collect(Collectors.toList());
    }


}
