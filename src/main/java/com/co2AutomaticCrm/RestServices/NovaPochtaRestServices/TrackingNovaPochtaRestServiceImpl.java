package com.co2AutomaticCrm.RestServices.NovaPochtaRestServices;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.Dto.RestDto.NovaPochtaDto.TrackingDto;
import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaAddress;
import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaTrackingDocument;
import com.co2AutomaticCrm.RestDao.RestNovaPochtaDao.TrackingRestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TrackingNovaPochtaRestServiceImpl implements TrackingNovaPochtaRestService {

    @Autowired
    private TrackingRestDao trackingRestDao;

    @Autowired
    private Mapper<NovaPochtaAddress, TrackingDto> trackingDtoToAddressMapper;

    @Override
    public Optional<NovaPochtaAddress> getAddress(NovaPochtaTrackingDocument novaPochtaTrackingDocument) {

        return Optional.of(trackingDtoToAddressMapper.toEntity(trackingRestDao.getInfo(novaPochtaTrackingDocument).orElse(null)));

    }
}
