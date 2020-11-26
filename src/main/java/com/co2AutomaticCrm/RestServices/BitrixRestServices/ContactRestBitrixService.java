package com.co2AutomaticCrm.RestServices.BitrixRestServices;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixContact;

import java.util.Optional;

public interface ContactRestBitrixService {

    Optional<BitrixContact> get(Integer id);

}
