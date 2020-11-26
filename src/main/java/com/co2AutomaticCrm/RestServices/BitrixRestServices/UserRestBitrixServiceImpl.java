package com.co2AutomaticCrm.RestServices.BitrixRestServices;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixUser;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.UserRestBitrixDto;
import com.co2AutomaticCrm.RestDao.RestBitrixDao.UserRestBitrixDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRestBitrixServiceImpl implements UserRestBitrixService {

    @Autowired
    private UserRestBitrixDao userRestBitrixDao;


    @Autowired
    private Mapper<BitrixUser, UserRestBitrixDto> mapper;

    @Override
    public Optional<BitrixUser> get(Long id) {

        return Optional.ofNullable(mapper.toEntity(userRestBitrixDao.get(id).orElse(null)));

    }

    @Override
    public Optional<BitrixUser> getByEmail(String email) {

        return Optional.ofNullable(mapper.toEntity(userRestBitrixDao.getByEmail(email).orElse(null)));

    }

}
