package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.BitrixDealDemandDao;
import com.co2AutomaticCrm.Dao.ProductDao;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleAmountDecreasingException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.InsufficientProductAmountException;
import com.co2AutomaticCrm.Models.BitrixDealDemand;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Models.ProductLine;
import com.co2AutomaticCrm.SyncUtils.ProductsBitrixRestSynchronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DealDemandServiceImpl implements DealDemandService {

    @Autowired
    private BitrixDealDemandDao demandDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductsBitrixRestSynchronizer productsBitrixRestSynchronizer;

    @Override
    public Optional<BitrixDealDemand> findById(long id) {
        return demandDao.findById(id);
    }

    @Override
    public Optional<BitrixDealDemand> findByBitrixDealId(long id) {
        return demandDao.findByBitrixDealId(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public BitrixDealDemand save(BitrixDealDemand demand) throws InsufficientProductAmountException, ImpossibleRestUpdateEntityException {

        if (Objects.isNull(demand) || demand.getBitrixDealId() == 0) return null;

        validateProductAmountInProductLines(demand.getProductLines());

        BitrixDealDemand retDealDem = demandDao.save(demand);

        List<Product> productsForSync = retDealDem.getProductLines().stream().map(ProductLine::getProduct).collect(Collectors.toList());

        productsBitrixRestSynchronizer.synchronizeProducts(productsForSync);

        return retDealDem;

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void deleteByBitrixDealId(Long id) throws ImpossibleRestUpdateEntityException {

        if (Objects.isNull(id) || id == 0) return;

        Optional<BitrixDealDemand> demandOpt = demandDao.findByBitrixDealId(id);

        if (!demandOpt.isPresent()) return;

        BitrixDealDemand dealDemand = demandOpt.get();

        returnProductAmountBeforeDelete(dealDemand.getProductLines());

        List<Product> productsForSync = dealDemand.getProductLines().stream().map(ProductLine::getProduct).collect(Collectors.toList());

        demandDao.delete(demandOpt.get());

        productsBitrixRestSynchronizer.synchronizeProducts(productsForSync);


    }

    @Override
    public List<BitrixDealDemand> findAll() {
        return demandDao.findAll();
    }

    @Override
    public List<BitrixDealDemand> saveAll(List<BitrixDealDemand> demands) {
        return demandDao.saveAll(demands);
    }


    private void validateProductAmountInProductLines(List<ProductLine> productLines) throws InsufficientProductAmountException {
        for (ProductLine productLineForVal : productLines) {
            try {
                productLineForVal.getProduct().decreaseAmount(productLineForVal.getAmount());
            } catch (ImpossibleAmountDecreasingException e) {
                throw new InsufficientProductAmountException("Недостаточное количество товара: " + productLineForVal.getProduct().getName());
            }
        }

    }

    private void returnProductAmountBeforeDelete(List<ProductLine> productLines) {
        for (ProductLine productLineForVal : productLines) {
            productLineForVal.getProduct().increaseAmount(productLineForVal.getAmount());
            productDao.save(productLineForVal.getProduct());
        }

    }

}
