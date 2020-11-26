package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.RestNovaPochtaDroMappers;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.Dto.RestDto.NovaPochtaDto.TrackingDto;
import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaAddress;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class TrackingDtoToAddressMapper implements Mapper<NovaPochtaAddress, TrackingDto> {

    @Autowired
    private ModelMapper mapper;

    public NovaPochtaAddress toEntity(TrackingDto trackingDto) {
        return Objects.isNull(trackingDto) ? null : mapper.map(trackingDto, NovaPochtaAddress.class);
    }

    public TrackingDto toDto(NovaPochtaAddress novaPochtaAddress) {
        return Objects.isNull(novaPochtaAddress) ? null : mapper.map(novaPochtaAddress, TrackingDto.class);
    }

}
