package com.co2AutomaticCrm.Dao.ManDaos;

import com.co2AutomaticCrm.Models.HelpModels.GenProductManipulation;
import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductManipulationRepExtension {

    List<GenProductManipulation> findAllByProductId(Long id);

    Page<GenProductManipulation> findAllByProductIdWithPagination(Long id, Pageable pageable);

    Page<GenProductManipulation> findAllByProductIdAndTypeWithPagination(Long id, ProductManipulationType productManipulationType, Pageable pageable);

}
