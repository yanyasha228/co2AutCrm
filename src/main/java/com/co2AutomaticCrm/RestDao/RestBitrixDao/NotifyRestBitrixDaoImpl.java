package com.co2AutomaticCrm.RestDao.RestBitrixDao;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixNotify;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.PostNotifyBitrixRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.PostNotifyBitrixRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class NotifyRestBitrixDaoImpl implements NotifyRestBitrixDao {


    @Value("${rest.bitrix.api.token}")
    private String apiToken;

    @Value("${rest.bitrix.api.post.notify.contactBitrixIdAndMessage.postNotify}")
    private String postNotifyUri;

    @Autowired
    private BitrixRestApiExchanger bitrixRestApiExchanger;

    @Override
    public boolean post(BitrixNotify bitrixNotify) {

        ResponseEntity<PostNotifyBitrixRestResponse> postNotifyResponseEntity = bitrixRestApiExchanger.exchange(String.format(postNotifyUri, apiToken), HttpMethod.POST, new PostNotifyBitrixRestRequest(bitrixNotify), PostNotifyBitrixRestResponse.class);

        if (postNotifyResponseEntity.getStatusCode() == HttpStatus.OK) {

            return postNotifyResponseEntity.getBody().isResult();
        }

        return false;
    }
}
