package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.RestBitrixDtoMappers;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixUser;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.UserRestBitrixDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserRestBitrixDtoToBitrixUserMapper implements Mapper<BitrixUser, UserRestBitrixDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BitrixUser toEntity(UserRestBitrixDto dto) {
        return Objects.isNull(dto) ? null : modelMapper.map(dto, BitrixUser.class);
    }

    @Override
    public UserRestBitrixDto toDto(BitrixUser entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, UserRestBitrixDto.class);
    }
}
