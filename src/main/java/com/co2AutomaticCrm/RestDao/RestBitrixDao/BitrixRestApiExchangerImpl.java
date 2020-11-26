package com.co2AutomaticCrm.RestDao.RestBitrixDao;

import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests.BitrixRestRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Responses.BitrixRestResponse;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class BitrixRestApiExchangerImpl implements BitrixRestApiExchanger {

    @Autowired
    private RestTemplate restTemplate;

    private RateLimiter rateLimiter = RateLimiter.create(2);

    @Override
    public <REQ extends BitrixRestRequest, RES extends BitrixRestResponse> ResponseEntity<RES> exchange(String url, HttpMethod method, REQ requestEntity, Class<RES> responseEntityClass) {

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        rateLimiter.acquire();

        return restTemplate.exchange(url, method, new HttpEntity<>(requestEntity, headers), responseEntityClass);

    }

}
