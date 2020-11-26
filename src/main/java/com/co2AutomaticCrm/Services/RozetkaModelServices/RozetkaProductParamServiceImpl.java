package com.co2AutomaticCrm.Services.RozetkaModelServices;

import com.co2AutomaticCrm.Dao.RozetkaModelsDao.RozetkaProductParamDao;
import com.co2AutomaticCrm.Models.RozetkaModels.RozetkaProductParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class RozetkaProductParamServiceImpl implements RozetkaProductParamService {

    @Autowired
    private RozetkaProductParamDao productParamDao;

    @Override
    public Page<RozetkaProductParam> findParamsWithPagination(String nonFullName, Pageable pageable) {
        Page<RozetkaProductParam> paramsForRet;
        if (!Objects.isNull(nonFullName) && !nonFullName.isEmpty()) {
            paramsForRet = findAllByNonFullName(nonFullName, pageable);
        } else {
            paramsForRet = findAllWithPagination(pageable);
        }
        return paramsForRet;
    }

    @Override
    public Page<RozetkaProductParam> findAllWithPagination(Pageable pageable) {
        return productParamDao.findAll(pageable);
    }

    @Override
    public RozetkaProductParam save(RozetkaProductParam productParam) {
        return productParamDao.save(productParam);
    }

    @Override
    public List<RozetkaProductParam> findAll() {
        return productParamDao.findAll();
    }

    @Override
    public Optional<RozetkaProductParam> findByName(String name) {
        return productParamDao.findByName(name);
    }

    @Override
    public Page<RozetkaProductParam> findAllByName(String name, Pageable pageable) {
        return productParamDao.findByName(name, pageable);
    }

    @Override
    public Page<RozetkaProductParam> findAllByNonFullName(String nonFullName, Pageable pageable) {
        return productParamDao.findRozetkaProductParamByNameIgnoreCaseContaining(nonFullName, pageable);
    }

    @Override
    public void delete(Long id) {
        productParamDao.deleteById(id);
    }

}
