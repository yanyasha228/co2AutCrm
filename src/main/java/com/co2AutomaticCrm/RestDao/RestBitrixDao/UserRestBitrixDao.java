package com.co2AutomaticCrm.RestDao.RestBitrixDao;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.UserRestBitrixDto;

import java.util.Optional;

public interface UserRestBitrixDao {

    Optional<UserRestBitrixDto> get(Long id);

    Optional<UserRestBitrixDto> getByEmail(String email);

}
