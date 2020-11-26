package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Models.SupplyProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SupplyProviderService {

    Optional<SupplyProvider> findById(int id);

    void delete(int id);

    SupplyProvider save(SupplyProvider supplyProvider);

    List<SupplyProvider> findAll();

    Page<SupplyProvider> findAllWithPagination(Pageable pageable);

    List<SupplyProvider> findSupplyProvidersByNameIgnoreCaseContaining(String nonFullName);

    Page<SupplyProvider> findSupplyProvidersByNameIgnoreCaseContainingWithPagination(String nonFullName, Pageable pageable);

    Optional<SupplyProvider> findSupplyProviderByName(String name);

    Page<SupplyProvider> findSupplyProvidersWithPagination(String nonFullName, Pageable pageable);

}
