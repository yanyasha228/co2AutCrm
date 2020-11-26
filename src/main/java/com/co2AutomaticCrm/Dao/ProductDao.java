package com.co2AutomaticCrm.Dao;

import com.co2AutomaticCrm.Models.Group;
import com.co2AutomaticCrm.Models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductDao extends JpaRepository<Product, Integer> {

    List<Product> findAllByGroup(Group group);

    List<Product> findAllByAvailability(Boolean availability);

    List<Product> findAllByGroupAndAvailability(Group group, Boolean availability);

    Product save(Product product);

    Optional<Product> findById(Long id);

    Optional<Product> findProductByName(String name);

    Optional<Product> findByBitrixId(Long bitrixId);

    List<Product> findProductsByNameIgnoreCaseContaining(String nonFullName);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findProductsByNameIgnoreCaseContaining(String nonFullName,
                                                         Pageable pageable);

    Page<Product> findProductsByGroup(Group group,
                                      Pageable pageable);

    Page<Product> findProductsByAvailability(boolean availability,
                                             Pageable pageable);

    Page<Product> findProductsByGroupAndAvailability(Group group,
                                                     boolean availability,
                                                     Pageable pageable);

    Page<Product> findProductsByNameIgnoreCaseContainingAndGroup(String nonFullName,
                                                                 Group group,
                                                                 Pageable pageable);

    Page<Product> findProductsByNameIgnoreCaseContainingAndAvailability(String nonFullName,
                                                                        boolean availability,
                                                                        Pageable pageable);

    Page<Product> findProductsByNameIgnoreCaseContainingAndGroupAndAvailability(String nonFullName,
                                                                                Group group,
                                                                                boolean availability,
                                                                                Pageable pageable);

}
