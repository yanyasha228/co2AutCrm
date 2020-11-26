package com.co2AutomaticCrm.RestDao.RestNovaPochtaDao;

import com.co2AutomaticCrm.Models.Dto.RestDto.NovaPochtaDto.TrackingDto;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestNovapochtaModels.Requests.TrackingNovaPochtaRequest;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestNovapochtaModels.Responses.TrackingNovapochtaResponse;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestNovapochtaModels.TrackingNovaPochtaRequestBuilder;
import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaTrackingDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;


@Component
public class TrackingRestDaoImpl implements TrackingRestDao {

    @Autowired
    private TrackingNovaPochtaRequestBuilder trackingNovapochtaRequestBuilder;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${rest.novapochta.api.url}")
    private String novaPochtaApiUrl;

    @Override
    public Optional<TrackingDto> getInfo(NovaPochtaTrackingDocument novaPochtaTrackingDocument) {

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<TrackingNovaPochtaRequest> entity = new HttpEntity<>(trackingNovapochtaRequestBuilder.build(novaPochtaTrackingDocument), headers);

        ResponseEntity<TrackingNovapochtaResponse> productsUpdateResponseEntity = restTemplate.exchange(novaPochtaApiUrl, HttpMethod.POST, entity, TrackingNovapochtaResponse.class);

        if (productsUpdateResponseEntity.getStatusCode() == HttpStatus.OK) {

            if (productsUpdateResponseEntity.getBody().isSuccess())
                return Optional.of(productsUpdateResponseEntity.getBody().getTrackingDtos().get(0));
        }

        return Optional.empty();

    }

}
