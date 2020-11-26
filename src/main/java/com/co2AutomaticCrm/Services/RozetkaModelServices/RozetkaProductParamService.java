package com.co2AutomaticCrm.Services.RozetkaModelServices;

import com.co2AutomaticCrm.Models.RozetkaModels.RozetkaProductParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RozetkaProductParamService {

    Page<RozetkaProductParam> findParamsWithPagination(String nonFullName, Pageable pageable);

    Page<RozetkaProductParam> findAllWithPagination(Pageable pageable);

    RozetkaProductParam save(RozetkaProductParam productParam);

    List<RozetkaProductParam> findAll();

    Optional<RozetkaProductParam> findByName(String name);

    Page<RozetkaProductParam> findAllByName(String name, Pageable pageable);

    Page<RozetkaProductParam> findAllByNonFullName(String nonFullName, Pageable pageable);

    void delete(Long id);

}
