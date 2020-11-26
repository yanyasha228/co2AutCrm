package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestNovapochtaModels;

import com.co2AutomaticCrm.Models.HelpRestModels.HelpRestNovapochtaModels.Requests.TrackingNovaPochtaRequest;
import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaTrackingDocument;

public interface TrackingNovaPochtaRequestBuilder {

    TrackingNovaPochtaRequest build(NovaPochtaTrackingDocument novaPochtaTrackingDocument);
}
