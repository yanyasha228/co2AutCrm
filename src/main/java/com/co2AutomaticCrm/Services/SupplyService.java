package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleAmountDecreasingException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.InsufficientProductAmountException;
import com.co2AutomaticCrm.Models.ModelEnums.SupplyStatus;
import com.co2AutomaticCrm.Models.Supply;
import com.co2AutomaticCrm.Models.SupplyProvider;
import com.co2AutomaticCrm.Models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SupplyService {

    Optional<Supply> findById(long id);

    Supply save(Supply supply,
                User user,
                int supplyProviderId,
                SupplyStatus supplyStatus,
                Long[] productLineIdInput,
                Integer[] productLineProductQua,
                Long[] productLineIdInputDependent,
                Integer[] productLineProductQuaDependent) throws ImpossibleEntitySaveUpdateException, ImpossibleAmountDecreasingException, ImpossibleRestUpdateEntityException, InsufficientProductAmountException;

    Page<Supply> findAllWithPagination(Pageable pageable);

    List<Supply> findAll();

    Page<Supply> findSuppliesBySupplyProvider(SupplyProvider supplyProvider,
                                              Pageable pageable);

    Page<Supply> findSuppliesBySupplyStatus(SupplyStatus supplyStatus,
                                            Pageable pageable);

    Page<Supply> findSuppliesBySupplyProviderAndSupplyStatus(SupplyProvider supplyProvider,
                                                             SupplyStatus supplyStatus,
                                                             Pageable pageable);

    Page<Supply> findSuppliesWithPagination(SupplyProvider supplyProvider,
                                            SupplyStatus supplyStatus,
                                            Pageable pageable);
}
