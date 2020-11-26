package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.BitrixModelToLocalModelMappers;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixUser;
import com.co2AutomaticCrm.Models.Worker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BitrixUserToUserMapper implements Mapper<Worker, BitrixUser> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Worker toEntity(BitrixUser dto) {
        if (Objects.isNull(dto)) return null;

        return modelMapper.map(dto, Worker.class);
    }

    @Override
    public BitrixUser toDto(Worker entity) {
        return null;
    }

}
