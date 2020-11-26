package com.co2AutomaticCrm.RestDao.RestBitrixDao;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.DealRestBitrixDto;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.GetBitrixDealsByStageIdRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.GetDealBitrixRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.UpdateDealBitrixRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.GetBitrixDealsByStageIdResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.GetDealBitrixRestResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.UpdateDealBitrixRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class DealRestBitrixDaoImpl implements DealRestBitrixDao {

    @Value("${rest.bitrix.api.token}")
    private String apiToken;

    @Value("${rest.bitrix.api.post.deal.dealBitrixId.getDealById}")
    private String getDealByIdUri;

    @Value("${rest.bitrix.api.post.deal.bitrixDeal.update}")
    private String updateDealUri;

    @Value("${rest.bitrix.api.post.deal.filters.getDealListByFilters}")
    private String getDealListByFilters;

    @Autowired
    private BitrixRestApiExchanger bitrixRestApiExchanger;

    @Override
    public Optional<DealRestBitrixDto> get(Integer id) {

        if (id == 0) return Optional.empty();

        ResponseEntity<GetDealBitrixRestResponse> getDealByIdEntity = bitrixRestApiExchanger.exchange(String.format(getDealByIdUri, apiToken), HttpMethod.POST, new GetDealBitrixRestRequest(id), GetDealBitrixRestResponse.class);

        if (getDealByIdEntity.getStatusCode() == HttpStatus.OK) {

            return Optional.of(getDealByIdEntity.getBody().getDealRestBitrixDto());
        }

        return Optional.empty();
    }

    @Override
    public Optional<DealRestBitrixDto> update(DealRestBitrixDto dealRestBitrixDto) throws ImpossibleRestUpdateEntityException {

        if (dealRestBitrixDto.getId() == 0)
            throw new ImpossibleRestUpdateEntityException("Attempt to update entity without ID");

        ResponseEntity<UpdateDealBitrixRestResponse> dealUpdateResponseEntity = bitrixRestApiExchanger.exchange(String.format(updateDealUri, apiToken), HttpMethod.POST, new UpdateDealBitrixRestRequest(dealRestBitrixDto), UpdateDealBitrixRestResponse.class);

        if (dealUpdateResponseEntity.getStatusCode() == HttpStatus.OK) {

            if (dealUpdateResponseEntity.getBody().isResult()) {
                return Optional.of(dealRestBitrixDto);

            }
        }

        return Optional.empty();

    }

    @Override
    public List<DealRestBitrixDto> getByStageId(String stageId) {

        if (Objects.isNull(stageId)) {
            return Collections.emptyList();
        } else if (stageId.isEmpty()) {
            return Collections.emptyList();
        }


        ResponseEntity<GetBitrixDealsByStageIdResponse> getDealByFiltersResp = bitrixRestApiExchanger.exchange(String.format(getDealListByFilters, apiToken), HttpMethod.POST, new GetBitrixDealsByStageIdRequest(stageId), GetBitrixDealsByStageIdResponse.class);

        if (getDealByFiltersResp.getStatusCode() == HttpStatus.OK) {

            return getDealByFiltersResp.getBody().getResult();

        }

        return Collections.emptyList();

    }
}

