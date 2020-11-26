package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.InsufficientProductAmountException;
import com.co2AutomaticCrm.Models.BitrixDealDemand;

import java.util.List;
import java.util.Optional;

public interface DealDemandService {

    Optional<BitrixDealDemand> findById(long id);

    Optional<BitrixDealDemand> findByBitrixDealId(long id);

    BitrixDealDemand save(BitrixDealDemand demand) throws InsufficientProductAmountException, ImpossibleRestUpdateEntityException;

    void deleteByBitrixDealId(Long id) throws ImpossibleRestUpdateEntityException;

    List<BitrixDealDemand> findAll();

    List<BitrixDealDemand> saveAll(List<BitrixDealDemand> demands);


}
