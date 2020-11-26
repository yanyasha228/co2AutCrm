package com.co2AutomaticCrm.RestDao.RestBitrixDao;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRowRestBitrixDto;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.GetProductRowsByDealIdRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.UpdateProductRowsByDealIdRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.GetProductRowsByDealIdRestResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.UpdateProductRowsByDealIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class ProductRowRestBitrixDaoImpl implements ProductRowRestBitrixDao {


    @Value("${rest.bitrix.api.token}")
    private String apiToken;

    @Value("${rest.bitrix.api.post.deal.productrows.dealBitrixId.getProductRowsByDealId}")
    private String getProductRowsByDealIdUri;

    @Value("${rest.bitrix.api.post.deal.productrows.dealBitrixIdAndProductRowBitrixDtos.setProductRowsByDealId}")
    private String updateProductRowsByDealIdUri;

    @Autowired
    private BitrixRestApiExchanger bitrixRestApiExchanger;


    @Override
    public List<ProductRowRestBitrixDto> getByDealId(Long id) {

        if (id == 0) return Collections.emptyList();

        ResponseEntity<GetProductRowsByDealIdRestResponse> getManagerByIdEntity = bitrixRestApiExchanger.exchange(String.format(getProductRowsByDealIdUri, apiToken), HttpMethod.POST, new GetProductRowsByDealIdRestRequest(id), GetProductRowsByDealIdRestResponse.class);

        if (getManagerByIdEntity.getStatusCode() == HttpStatus.OK) {

            return getManagerByIdEntity.getBody().getProductRowRestBitrixDtoList();
        }

        return Collections.emptyList();
    }

    @Override
    public boolean update(Long bitrixDealId, List<ProductRowRestBitrixDto> productRowList) {
        if (bitrixDealId == 0 || Objects.isNull(productRowList) || productRowList.isEmpty()) return false;

        ResponseEntity<UpdateProductRowsByDealIdResponse> updateProdRowsEntity = bitrixRestApiExchanger.exchange(String.format(updateProductRowsByDealIdUri, apiToken), HttpMethod.POST, new UpdateProductRowsByDealIdRestRequest(bitrixDealId, productRowList), UpdateProductRowsByDealIdResponse.class);

        if (updateProdRowsEntity.getStatusCode() == HttpStatus.OK) {
            return updateProdRowsEntity.getBody().isResult();
        }

        return false;

    }

}
