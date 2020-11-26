package com.co2AutomaticCrm.RestServices.BitrixRestServices;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixUser;

import java.util.Optional;

public interface UserRestBitrixService {

    Optional<BitrixUser> get(Long id);

    Optional<BitrixUser> getByEmail(String email);

}
