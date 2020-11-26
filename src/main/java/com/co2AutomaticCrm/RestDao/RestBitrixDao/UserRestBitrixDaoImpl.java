package com.co2AutomaticCrm.RestDao.RestBitrixDao;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.UserRestBitrixDto;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.GetUserBitrixRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.GetUserByEmailRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.GetUserBitrixRestResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.GetUserByEmailRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

//Implementation of user DAO lop
@Component
public class UserRestBitrixDaoImpl implements UserRestBitrixDao {


    @Value("${rest.bitrix.api.token}")
    private String apiToken;

    @Value("${rest.bitrix.api.post.user.userBitrixId.getUser}")
    private String getManagerUri;

    @Value("${rest.bitrix.api.post.user.userBitrixId.getUsersByFilters}")
    private String getManagerByFiltersUri;


    @Autowired
    private BitrixRestApiExchanger bitrixRestApiExchanger;

    @Override
    public Optional<UserRestBitrixDto> get(Long id) {

        if (id == 0) return Optional.empty();

        ResponseEntity<GetUserBitrixRestResponse> getManagerByIdEntity = bitrixRestApiExchanger.exchange(String.format(getManagerUri, apiToken), HttpMethod.POST, new GetUserBitrixRestRequest(id), GetUserBitrixRestResponse.class);

        if (getManagerByIdEntity.getStatusCode() == HttpStatus.OK) {

            return Optional.of(getManagerByIdEntity.getBody().getUserRestBitrixDtos().get(0));
        }

        return Optional.empty();
    }

    @Override
    public Optional<UserRestBitrixDto> getByEmail(String email) {

        if (Objects.isNull(email) || email.isEmpty()) return Optional.empty();

        ResponseEntity<GetUserByEmailRestResponse> getManagerByEmailEntity = bitrixRestApiExchanger.exchange(String.format(getManagerByFiltersUri, apiToken), HttpMethod.POST, new GetUserByEmailRestRequest(email), GetUserByEmailRestResponse.class);

        if (getManagerByEmailEntity.getStatusCode() == HttpStatus.OK) {

            if (!getManagerByEmailEntity.getBody().getUsers().isEmpty())

                return Optional.of(getManagerByEmailEntity.getBody().getUsers().get(0));


        }

        return Optional.empty();

    }

}
