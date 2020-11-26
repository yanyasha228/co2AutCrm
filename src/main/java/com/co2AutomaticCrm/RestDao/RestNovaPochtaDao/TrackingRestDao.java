package com.co2AutomaticCrm.RestDao.RestNovaPochtaDao;

import com.co2AutomaticCrm.Models.Dto.RestDto.NovaPochtaDto.TrackingDto;
import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaTrackingDocument;

import java.util.Optional;

public interface TrackingRestDao {

    Optional<TrackingDto> getInfo(NovaPochtaTrackingDocument novaPochtaTrackingDocument);

}
