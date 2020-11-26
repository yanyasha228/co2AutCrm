package com.co2AutomaticCrm.Dao;

import com.co2AutomaticCrm.Models.ModelEnums.SupplyStatus;
import com.co2AutomaticCrm.Models.Supply;
import com.co2AutomaticCrm.Models.SupplyProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SupplyDao extends JpaRepository<Supply, Long> {

    Optional<Supply> findById(long id);

    Page<Supply> findAll(Pageable pageable);

    List<Supply> findAll();

    Page<Supply> findSuppliesBySupplyProvider(SupplyProvider supplyProvider,
                                              Pageable pageable);

    Page<Supply> findSuppliesBySupplyStatus(SupplyStatus supplyStatus,
                                            Pageable pageable);

    Page<Supply> findSuppliesBySupplyProviderAndSupplyStatus(SupplyProvider supplyProvider,
                                                             SupplyStatus supplyStatus,
                                                             Pageable pageable);
//    @Query(value = "" , nativeQuery = true)
//    List<Supply> findAllByProductId(@Param("id") long id);
//
//    @Query(value = "" ,
//
//            countQuery = "",
//            nativeQuery = true)
//    Page<Supply> findAllByProductIdWithPagination(@Param("id") long id , Pageable pageable);

}
