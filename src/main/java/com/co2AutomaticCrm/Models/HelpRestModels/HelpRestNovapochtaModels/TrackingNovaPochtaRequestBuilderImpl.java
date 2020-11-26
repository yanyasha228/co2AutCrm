package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestNovapochtaModels;

import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestNovapochtaModels.HelpDto.TrackingMethodPropertiesDto;
import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestNovapochtaModels.Requests.TrackingNovaPochtaRequest;
import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaTrackingDocument;
import org.springframework.stereotype.Component;

@Component
public class TrackingNovaPochtaRequestBuilderImpl implements TrackingNovaPochtaRequestBuilder {

    @Override
    public TrackingNovaPochtaRequest build(NovaPochtaTrackingDocument novaPochtaTrackingDocument) {

        TrackingMethodPropertiesDto trackingMethodPropertiesDto = new TrackingMethodPropertiesDto();
        trackingMethodPropertiesDto.getDocuments().add(novaPochtaTrackingDocument);
        TrackingNovaPochtaRequest trackingNovapochtaRequest = new TrackingNovaPochtaRequest();
        trackingNovapochtaRequest.setMethodProperties(trackingMethodPropertiesDto);
        return trackingNovapochtaRequest;
    }

}
