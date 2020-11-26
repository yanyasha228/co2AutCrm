package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Models.HelpModels.GenProductManipulation;
import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GenProductManipulationService {

    Page<GenProductManipulation> findAllByProductIdWithPagination(Long id, Pageable pageable);

    List<GenProductManipulation> findAllByProductId(Long id);

    Page<GenProductManipulation> findAllByProductIdAndTypeWithPagination(Long id, ProductManipulationType productManipulationType, Pageable pageable);

    Page<GenProductManipulation> findAllWithPagination(Long id, ProductManipulationType productManipulationType, Pageable pageable);
}
