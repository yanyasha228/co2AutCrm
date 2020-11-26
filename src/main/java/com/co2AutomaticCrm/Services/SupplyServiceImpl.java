package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.ProductDao;
import com.co2AutomaticCrm.Dao.SupplyDao;
import com.co2AutomaticCrm.Dao.SupplyProviderDao;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleAmountDecreasingException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.InsufficientProductAmountException;
import com.co2AutomaticCrm.Models.ModelEnums.SupplyStatus;
import com.co2AutomaticCrm.Models.*;
import com.co2AutomaticCrm.SyncUtils.ProductsBitrixRestSynchronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplyServiceImpl implements SupplyService {

    @Autowired
    private SupplyDao supplyDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SupplyDependentDemandService supplyDependentDemandService;

    @Autowired
    private SupplyProviderDao supplyProviderDao;


    @Autowired
    private ProductsBitrixRestSynchronizer productsBitrixRestSynchronizer;

    @Override
    public Optional<Supply> findById(long id) {
        return supplyDao.findById(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Supply save(Supply supply,
                       User user,
                       int supplyProviderId,
                       SupplyStatus supplyStatus,
                       Long[] productLineIdInput,
                       Integer[] productLineProductQua,
                       Long[] productLineIdInputDependent,
                       Integer[] productLineProductQuaDependent) throws ImpossibleEntitySaveUpdateException,
            ImpossibleAmountDecreasingException,
            ImpossibleRestUpdateEntityException,
            InsufficientProductAmountException {

        supply.setSupplyProvider(supplyProviderDao.findById(supplyProviderId).get());
        supply.setSupplyStatus(supplyStatus);
        supply.setUser(user);

        List<ProductLine> productLines = new ArrayList<>();
        List<ProductLine> dependentProductLines = new ArrayList<>();
        SupplyDependentDemand supplyDependentDemand = null;

        if (productLineIdInput != null && productLineProductQua != null) {
            if (productLineIdInput.length != productLineProductQua.length)
                throw new ImpossibleEntitySaveUpdateException("ArrLenght doesnt match");
        } else throw new ImpossibleEntitySaveUpdateException("Null productLines dependent Arrays");

        if (productLineIdInputDependent != null && productLineProductQuaDependent != null) {
            if (productLineIdInputDependent.length != productLineProductQuaDependent.length)
                throw new ImpossibleEntitySaveUpdateException("ArrLenght doesnt match");
        }

        for (int i = 0; i < productLineIdInput.length; i++) {
            ProductLine newProductLine = new ProductLine();
            Product prodForProdLine = productDao.findById(productLineIdInput[i]).orElse(null);
            if (prodForProdLine != null) {

                newProductLine.setProduct(prodForProdLine);
                newProductLine.setAmount(productLineProductQua[i]);
                newProductLine.setManipulationMomentAmount(prodForProdLine.getAmount());

                productLines.add(newProductLine);

            }

        }

        if (productLineIdInputDependent != null && productLineProductQuaDependent != null) {
            if (productLineIdInputDependent.length != 0) {
                for (int i = 0; i < productLineIdInputDependent.length; i++) {
                    ProductLine newProductLine = new ProductLine();
                    Product prodForProdLine = productDao.findById(productLineIdInputDependent[i]).orElse(null);
                    if (prodForProdLine != null) {

                        newProductLine.setProduct(prodForProdLine);
                        newProductLine.setAmount(productLineProductQuaDependent[i]);
                        newProductLine.setManipulationMomentAmount(prodForProdLine.getAmount());
                        dependentProductLines.add(newProductLine);

                    }


                }
            }
        }

        validateProductAmountInProductLines(productLines);

        supply.setProductLines(productLines);

        if (dependentProductLines.size() > 0) {
            supplyDependentDemand = supplyDependentDemandService.create(user, dependentProductLines);
            supplyDependentDemand.setSupply(supply);
            supply.setSupplyDependentDemand(supplyDependentDemand);
        }

        Supply supplyToRet = supplyDao.save(supply);

        List<Product> list = supplyToRet.getProductLines().stream().map(ProductLine::getProduct).collect(Collectors.toList());

        if (!Objects.isNull(supplyToRet.getSupplyDependentDemand())) {
            list.addAll(supplyToRet.getSupplyDependentDemand().getProductLines().stream().map(ProductLine::getProduct).collect(Collectors.toList()));
        }

        productsBitrixRestSynchronizer.synchronizeProducts(list);

        return supplyToRet;
    }

    private void validateProductAmountInProductLines(List<ProductLine> productLines) {
        for (ProductLine productLineForVal : productLines) {
            productLineForVal.getProduct().increaseAmount(productLineForVal.getAmount());
        }

    }


    @Override
    public Page<Supply> findAllWithPagination(Pageable pageable) {
        return supplyDao.findAll(pageable);
    }

    @Override
    public List<Supply> findAll() {
        return supplyDao.findAll();
    }

    @Override
    public Page<Supply> findSuppliesBySupplyProvider(SupplyProvider supplyProvider,
                                                     Pageable pageable) {
        return supplyDao.findSuppliesBySupplyProvider(supplyProvider, pageable);
    }

    @Override
    public Page<Supply> findSuppliesBySupplyStatus(SupplyStatus supplyStatus,
                                                   Pageable pageable) {
        return supplyDao.findSuppliesBySupplyStatus(supplyStatus,
                pageable);
    }


    @Override
    public Page<Supply> findSuppliesBySupplyProviderAndSupplyStatus(SupplyProvider supplyProvider,
                                                                    SupplyStatus supplyStatus,
                                                                    Pageable pageable) {

        return supplyDao.findSuppliesBySupplyProviderAndSupplyStatus(supplyProvider,
                supplyStatus,
                pageable);
    }


    @Override
    public Page<Supply> findSuppliesWithPagination(SupplyProvider supplyProvider, SupplyStatus supplyStatus, Pageable pageable) {

        if (supplyProvider == null) {

            if (supplyStatus == null) {
                return findAllWithPagination(pageable);

            } else return findSuppliesBySupplyStatus(supplyStatus,
                    pageable);


        } else {

            if (supplyStatus == null) {
                return findSuppliesBySupplyProvider(supplyProvider,
                        pageable);

            } else return findSuppliesBySupplyProviderAndSupplyStatus(supplyProvider,
                    supplyStatus,
                    pageable);
        }

    }

}
