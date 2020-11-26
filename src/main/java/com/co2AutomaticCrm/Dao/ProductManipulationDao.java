package com.co2AutomaticCrm.Dao;

import com.co2AutomaticCrm.Models.ProductManipulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductManipulationDao<T extends ProductManipulation> extends JpaRepository<T, Long> {

    Optional<T> findById(Long id);

}
