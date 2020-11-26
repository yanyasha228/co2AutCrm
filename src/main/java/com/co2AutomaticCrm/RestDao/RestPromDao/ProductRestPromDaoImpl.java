package com.co2AutomaticCrm.RestDao.RestPromDao;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestPromDto.ProductRestPromDto;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestPromModels.EditProductsPromRestResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestPromModels.ProductPromRestResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestPromModels.ProductsListPromRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class ProductRestPromDaoImpl implements ProductRestPromDao {

    @Value("${rest.prom.api.token}")
    private String apiToken;

    @Value("${rest.prom.api.get.products.list.group_id}")
    private String getProductsByGroupIdUri;

    @Value("${rest.prom.api.post.products.edit}")
    private String postProductsEditUri;

    @Value("${rest.prom.api.get.products.id}")
    private String getProductByIdUri;

    private static Logger logger = LoggerFactory.getLogger(ProductRestPromDaoImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<ProductRestPromDto> getProductById(Long id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", apiToken));
        HttpEntity<ProductPromRestResponse> entity = new HttpEntity<ProductPromRestResponse>(headers);

        ResponseEntity<ProductPromRestResponse> productResponseEntity = restTemplate.exchange(String.format(getProductByIdUri, String.valueOf(id)), HttpMethod.GET, entity, ProductPromRestResponse.class);

        if (productResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (productResponseEntity.getBody() != null) {
                return Optional.of(productResponseEntity.getBody().getProduct());

            }
        }

        return Optional.empty();
    }

    @Override
    public List<ProductRestPromDto> getProductsByGroupId(int groupId) {
        List<ProductRestPromDto> productDtos = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", apiToken));
        HttpEntity<ProductsListPromRestResponse> entity = new HttpEntity<ProductsListPromRestResponse>(headers);

        ResponseEntity<ProductsListPromRestResponse> productsListResponseEntity = restTemplate.exchange(String.format(getProductsByGroupIdUri, String.valueOf(groupId)), HttpMethod.GET, entity, ProductsListPromRestResponse.class);

        if (productsListResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (productsListResponseEntity.getBody() != null) {
                productDtos.addAll(productsListResponseEntity.getBody().getProducts());
                return productDtos;
            }
        }

        return Collections.emptyList();
    }


    @Override
    public List<ProductRestPromDto> postProducts(List<ProductRestPromDto> productDtos) {

        List<ProductRestPromDto> productDtoResponseList = new ArrayList<>();
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", String.format("Bearer %s", apiToken));

        HttpEntity<List<ProductRestPromDto>> entity = new HttpEntity<List<ProductRestPromDto>>(productDtos, headers);

        ResponseEntity<EditProductsPromRestResponse> productsEditResponseEntity = restTemplate.exchange(postProductsEditUri, HttpMethod.POST, entity, EditProductsPromRestResponse.class);

        productsEditResponseEntity.toString();
        if (productsEditResponseEntity.getStatusCode() == HttpStatus.OK) {
            if (productsEditResponseEntity.getBody() != null) {
                for (ProductRestPromDto productDtoIter : productDtos) {
                    for (Integer prodUpId : productsEditResponseEntity
                            .getBody().getProcessed_ids()
                    ) {
                        if (productDtoIter.getId() == prodUpId) productDtoResponseList.add(productDtoIter);
                    }
                }
                return productDtoResponseList;
            }
        }

        return Collections.emptyList();

    }
}
