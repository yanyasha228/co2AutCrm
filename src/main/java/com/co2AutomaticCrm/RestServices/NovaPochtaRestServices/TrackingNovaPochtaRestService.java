package com.co2AutomaticCrm.RestServices.NovaPochtaRestServices;

import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaAddress;
import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaTrackingDocument;

import java.util.Optional;

public interface TrackingNovaPochtaRestService {


    Optional<NovaPochtaAddress> getAddress(NovaPochtaTrackingDocument novaPochtaTrackingDocument);


}
