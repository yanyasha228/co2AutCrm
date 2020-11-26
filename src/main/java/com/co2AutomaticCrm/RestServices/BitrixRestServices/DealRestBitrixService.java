package com.co2AutomaticCrm.RestServices.BitrixRestServices;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;

import java.util.Optional;

public interface DealRestBitrixService {

    Optional<BitrixDeal> get(Integer id);

    Optional<BitrixDeal> update(BitrixDeal bitrixDeal) throws ImpossibleRestUpdateEntityException;

}
