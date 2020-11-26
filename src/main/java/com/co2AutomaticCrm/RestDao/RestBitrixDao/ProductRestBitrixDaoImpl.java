package com.co2AutomaticCrm.RestDao.RestBitrixDao;


import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRestBitrixDto;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.GetProductBitrixRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.GetProductListBitrixWithPaginationRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.PostProductBitrixRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.UpdateProductBitrixRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.GetProductBitrixRestResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.GetProductListBitrixWithPaginationResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.PostProductBitrixRestResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.UpdateProductBitrixRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProductRestBitrixDaoImpl implements ProductRestBitrixDao {

    @Value("${rest.bitrix.api.token}")
    private String apiToken;

    @Value("${rest.bitrix.api.post.product.productBitrixId.getProductById}")
    private String getProductByIdUri;

    @Value("${rest.bitrix.api.post.product.productRestBitrixDto.updateProduct}")
    private String updateProductByIdUri;

    @Value("${rest.bitrix.api.post.product.productRestBitrixDto.addProduct}")
    private String addProductUrl;

    @Value("${rest.bitrix.api.post.product.productRestBitrixDto.getProductListWithPagination}")
    private String getProductsListWithPaginationUrl;


    @Autowired
    private BitrixRestApiExchanger bitrixRestApiExchanger;


    @Override
    public Optional<ProductRestBitrixDto> post(ProductRestBitrixDto productRestBitrixDto) {

        ResponseEntity<PostProductBitrixRestResponse> productsEditResponseEntity = bitrixRestApiExchanger.exchange(String.format(addProductUrl, apiToken), HttpMethod.POST, new PostProductBitrixRestRequest(productRestBitrixDto), PostProductBitrixRestResponse.class);

        if (productsEditResponseEntity.getStatusCode() == HttpStatus.OK) {

            ResponseEntity<GetProductBitrixRestResponse> getProductEntity = bitrixRestApiExchanger.exchange(String.format(getProductByIdUri, apiToken), HttpMethod.POST, new GetProductBitrixRestRequest(productsEditResponseEntity.getBody().getAddedProductId()), GetProductBitrixRestResponse.class);

            if (getProductEntity.getStatusCode() == HttpStatus.OK) {
                return Optional.of(getProductEntity.getBody().getProductDto());
            } else return Optional.empty();
        }

        return Optional.empty();

    }

    @Override
    public Optional<ProductRestBitrixDto> update(ProductRestBitrixDto productRestBitrixDto) throws ImpossibleRestUpdateEntityException {

        if (productRestBitrixDto.getBitrixId() == 0)
            throw new ImpossibleRestUpdateEntityException("Attempt to update entity without ID");


        ResponseEntity<UpdateProductBitrixRestResponse> productsUpdateResponseEntity = bitrixRestApiExchanger.exchange(String.format(updateProductByIdUri, apiToken), HttpMethod.POST, new UpdateProductBitrixRestRequest(productRestBitrixDto), UpdateProductBitrixRestResponse.class);

        if (productsUpdateResponseEntity.getStatusCode() == HttpStatus.OK) {

            if (productsUpdateResponseEntity.getBody().isResult()) {
                return Optional.of(productRestBitrixDto);

            }
        }

        return Optional.empty();

    }

    @Override
    public List<ProductRestBitrixDto> updateAll(List<ProductRestBitrixDto> productRestBitrixDtos) throws ImpossibleRestUpdateEntityException {

        List<ProductRestBitrixDto> productRestBitrixDtoList = new ArrayList<>();

        for (ProductRestBitrixDto prodDto : productRestBitrixDtos) {
            if (update(prodDto).isPresent()) {
                productRestBitrixDtoList.add(prodDto);

                System.out.println("Product has been updated id : " + prodDto.getBitrixId());
            }
        }

        return productRestBitrixDtoList;

    }

    @Override
    public Optional<ProductRestBitrixDto> getByBitrixId(Long bitrix_id) {

        if (bitrix_id == 0) return Optional.empty();

        ResponseEntity<GetProductBitrixRestResponse> getProductByIdEntity = bitrixRestApiExchanger.exchange(String.format(getProductByIdUri, apiToken), HttpMethod.POST, new GetProductBitrixRestRequest(bitrix_id), GetProductBitrixRestResponse.class);

        if (getProductByIdEntity.getStatusCode() == HttpStatus.OK) {

            return Optional.of(getProductByIdEntity.getBody().getProductDto());
        }

        return Optional.empty();

    }

    @Override
    public List<ProductRestBitrixDto> getAll() {

        List<ProductRestBitrixDto> productRestBitrixDtos = new ArrayList<>();

        ResponseEntity<GetProductListBitrixWithPaginationResponse> productsListResponseEntity = bitrixRestApiExchanger.exchange(String.format(getProductsListWithPaginationUrl, apiToken), HttpMethod.POST, new GetProductListBitrixWithPaginationRequest(0), GetProductListBitrixWithPaginationResponse.class);


        if (productsListResponseEntity.getStatusCode() == HttpStatus.OK) {

            boolean next = false;

            int start = productsListResponseEntity.getBody().getNext();


            productRestBitrixDtos.addAll(productsListResponseEntity.getBody().getProductDtos());

            if (productsListResponseEntity.getBody().getNext() > 0) next = true;

            while (next) {

                ResponseEntity<GetProductListBitrixWithPaginationResponse> productsListResponseEntitySec = bitrixRestApiExchanger.exchange(String.format(getProductsListWithPaginationUrl, apiToken), HttpMethod.POST, new GetProductListBitrixWithPaginationRequest(start), GetProductListBitrixWithPaginationResponse.class);

                if (productsListResponseEntitySec.getStatusCode() == HttpStatus.OK) {

                    productRestBitrixDtos.addAll(productsListResponseEntitySec.getBody().getProductDtos());
                    start = productsListResponseEntitySec.getBody().getNext();
                    next = productsListResponseEntitySec.getBody().getNext() > 0;

                } else next = false;

            }

        }

        return productRestBitrixDtos;
    }

}
