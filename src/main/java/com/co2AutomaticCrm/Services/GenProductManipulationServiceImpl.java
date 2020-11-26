package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.ManDaos.GenProductManipulationDao;
import com.co2AutomaticCrm.Models.HelpModels.GenProductManipulation;
import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GenProductManipulationServiceImpl implements GenProductManipulationService {

    @Autowired
    private GenProductManipulationDao genProductManipulationDao;

    @Override
    public Page<GenProductManipulation> findAllByProductIdWithPagination(Long id, Pageable pageable) {
        return genProductManipulationDao.findAllByProductIdWithPagination(id, pageable);
    }

    @Override
    public List<GenProductManipulation> findAllByProductId(Long id) {

        return genProductManipulationDao.findAllByProductId(id);

    }

    @Override
    public Page<GenProductManipulation> findAllByProductIdAndTypeWithPagination(Long id, ProductManipulationType productManipulationType, Pageable pageable) {

        return genProductManipulationDao.findAllByProductIdAndTypeWithPagination(id, productManipulationType, pageable);

    }

    @Override
    public Page<GenProductManipulation> findAllWithPagination(Long id, ProductManipulationType productManipulationType, Pageable pageable) {

        if (!Objects.isNull(productManipulationType))
            return findAllByProductIdAndTypeWithPagination(id, productManipulationType, pageable);

        return findAllByProductIdWithPagination(id, pageable);
    }
}
