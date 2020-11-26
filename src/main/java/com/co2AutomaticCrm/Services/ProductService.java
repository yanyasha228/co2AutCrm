package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.Models.Group;
import com.co2AutomaticCrm.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllByGroup(Group group);

    List<Product> findAllByAvailability(Boolean availability);

    List<Product> findAllByGroupAndAvailability(Group group, Boolean availability);

    Product save(Product product) throws ImpossibleEntitySaveUpdateException;

    List<Product> save(List<Product> productList);

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> findByBitrixId(Long id);

    Optional<Product> findProductByName(String name);

    List<Product> findProductsByNonFullProductNameRegardlessOfTheWordsOrder(String nonFullProductName);

    List<Product> findProductsByNameIgnoreCaseContaining(String nonFullName);

    Page<Product> findAllWithPagination(Pageable pageable);

    Page<Product> findProductsByNameIgnoreCaseContainingWithPagination(String nonFullName,
                                                                       Pageable pageable);

    Page<Product> findProductsByAvailabilityWithPagination(boolean availability,
                                                           Pageable pageable);

    Page<Product> findProductsByGroupWithPagination(Group group,
                                                    Pageable pageable);

    Page<Product> findProductsByGroupAndAvailabilityWithPagination(Group group,
                                                                   boolean availability,
                                                                   Pageable pageable);

    Page<Product> findProductsByNameIgnoreCaseContainingAndGroupWithPagination(String nonFullName,
                                                                               Group group,
                                                                               Pageable pageable);

    Page<Product> findProductsByNameIgnoreCaseContainingAndAvailabilityWithPagination(String nonFullName,
                                                                                      boolean availability,
                                                                                      Pageable pageable);

    Page<Product> findProductsByNameIgnoreCaseContainingAndGroupAndAvailabilityWithPagination(String nonFullName,
                                                                                              Group group,
                                                                                              boolean availability,
                                                                                              Pageable pageable);

    public Page<Product> findProductsWithPagination(String nonFullProductName,
                                                    Group group,
                                                    Boolean availability,
                                                    Pageable pageable);
}
