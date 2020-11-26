package com.co2AutomaticCrm.Dao;


import com.co2AutomaticCrm.Models.SupplyProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SupplyProviderDao extends JpaRepository<SupplyProvider, Integer> {

    Optional<SupplyProvider> findById(int id);

    List<SupplyProvider> findAll();

    Page<SupplyProvider> findAll(Pageable pageable);

    List<SupplyProvider> findSupplyProvidersByNameIgnoreCaseContaining(String nonFullName);

    Page<SupplyProvider> findSupplyProvidersByNameIgnoreCaseContaining(String nonFullName, Pageable pageable);

    Optional<SupplyProvider> findSupplyProviderByName(String name);

}
