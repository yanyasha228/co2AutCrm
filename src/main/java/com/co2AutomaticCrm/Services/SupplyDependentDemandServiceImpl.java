package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.ProductDao;
import com.co2AutomaticCrm.Dao.SupplyDependentDemandDao;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleAmountDecreasingException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.InsufficientProductAmountException;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Models.ProductLine;
import com.co2AutomaticCrm.Models.SupplyDependentDemand;
import com.co2AutomaticCrm.Models.User;
import com.co2AutomaticCrm.SyncUtils.ProductsBitrixRestSynchronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplyDependentDemandServiceImpl implements SupplyDependentDemandService {

    @Autowired
    private SupplyDependentDemandDao demandDao;

    @Autowired
    private ProductsBitrixRestSynchronizer productsBitrixRestSynchronizer;

    @Autowired
    private ProductDao productDao;

    @Override
    public Optional<SupplyDependentDemand> findById(long id) {
        return demandDao.findById(id);
    }

    @Override
    public Optional<SupplyDependentDemand> findBySupplyId(long id) {
        return demandDao.findBySupplyId(id);
    }

    @Override
    public SupplyDependentDemand create(User user, List<ProductLine> productLines) throws InsufficientProductAmountException {
        validateProductAmountInProductLines(productLines);
        SupplyDependentDemand supplyDependentDemand = new SupplyDependentDemand();
        supplyDependentDemand.setProductLines(productLines);
        supplyDependentDemand.setUser(user);

        return supplyDependentDemand;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public SupplyDependentDemand save(SupplyDependentDemand demand) throws InsufficientProductAmountException, ImpossibleRestUpdateEntityException {

        if (Objects.isNull(demand) || Objects.isNull(demand.getSupply())) return null;

        validateProductAmountInProductLines(demand.getProductLines());

        SupplyDependentDemand retDemand = demandDao.save(demand);

        List<Product> productsForSync = retDemand.getProductLines().stream().map(ProductLine::getProduct).collect(Collectors.toList());

        productsBitrixRestSynchronizer.synchronizeProducts(productsForSync);

        return retDemand;
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
