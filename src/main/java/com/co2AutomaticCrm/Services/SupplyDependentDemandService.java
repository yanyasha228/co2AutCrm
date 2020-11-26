package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.InsufficientProductAmountException;
import com.co2AutomaticCrm.Models.ProductLine;
import com.co2AutomaticCrm.Models.SupplyDependentDemand;
import com.co2AutomaticCrm.Models.User;

import java.util.List;
import java.util.Optional;

public interface SupplyDependentDemandService {

    Optional<SupplyDependentDemand> findById(long id);

    Optional<SupplyDependentDemand> findBySupplyId(long id);

    SupplyDependentDemand create(User user, List<ProductLine> productLineList) throws InsufficientProductAmountException;

    SupplyDependentDemand save(SupplyDependentDemand demand) throws InsufficientProductAmountException, ImpossibleRestUpdateEntityException;

}
