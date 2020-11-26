package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.SupplyProviderDao;
import com.co2AutomaticCrm.Models.SupplyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SupplyProviderServiceImpl implements SupplyProviderService {

    @Autowired
    private SupplyProviderDao supplyProviderDao;

    @Override
    public Optional<SupplyProvider> findById(int id) {
        return supplyProviderDao.findById(id);
    }

    @Override
    public void delete(int id) {
        supplyProviderDao.deleteById(id);
    }

    @Override
    public SupplyProvider save(SupplyProvider supplyProvider) {
        return supplyProviderDao.save(supplyProvider);
    }

    @Override
    public List<SupplyProvider> findAll() {
        return supplyProviderDao.findAll();
    }

    @Override
    public Page<SupplyProvider> findAllWithPagination(Pageable pageable) {
        return supplyProviderDao.findAll(pageable);
    }

    @Override
    public List<SupplyProvider> findSupplyProvidersByNameIgnoreCaseContaining(String nonFullName) {
        return supplyProviderDao.findSupplyProvidersByNameIgnoreCaseContaining(nonFullName);
    }

    @Override
    public Page<SupplyProvider> findSupplyProvidersByNameIgnoreCaseContainingWithPagination(String nonFullName, Pageable pageable) {
        return supplyProviderDao.findSupplyProvidersByNameIgnoreCaseContaining(nonFullName, pageable);
    }

    @Override
    public Optional<SupplyProvider> findSupplyProviderByName(String name) {
        return supplyProviderDao.findSupplyProviderByName(name);
    }

    @Override
    public Page<SupplyProvider> findSupplyProvidersWithPagination(String nonFullName, Pageable pageable) {
        if (nonFullName.replaceAll(" ", "").isEmpty()) return findAllWithPagination(pageable);

        return findSupplyProvidersByNameIgnoreCaseContainingWithPagination(nonFullName, pageable);

    }

}
