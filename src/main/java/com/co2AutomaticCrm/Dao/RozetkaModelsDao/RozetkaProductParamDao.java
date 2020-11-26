package com.co2AutomaticCrm.Dao.RozetkaModelsDao;

import com.co2AutomaticCrm.Models.RozetkaModels.RozetkaProductParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RozetkaProductParamDao extends JpaRepository<RozetkaProductParam, Long> {

    Page<RozetkaProductParam> findByName(String name, Pageable pageable);

    Optional<RozetkaProductParam> findByName(String name);

    Page<RozetkaProductParam> findRozetkaProductParamByNameIgnoreCaseContaining(String nonFullName, Pageable pageable);

    Page<RozetkaProductParam> findAll(Pageable pageable);

}
