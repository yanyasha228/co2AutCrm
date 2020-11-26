package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.CorrectionDao;
import com.co2AutomaticCrm.Dao.ProductDao;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleAmountDecreasingException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.Models.Correction;
import com.co2AutomaticCrm.Models.HelpModels.ProductManipulationLine;
import com.co2AutomaticCrm.Models.ModelEnums.CorrectionType;
import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Models.ProductLine;
import com.co2AutomaticCrm.Models.User;
import com.co2AutomaticCrm.SyncUtils.ProductsBitrixRestSynchronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CorrectionServiceImpl implements CorrectionService {

    @Autowired
    private CorrectionDao correctionDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductsBitrixRestSynchronizer productsBitrixRestSynchronizer;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Correction saveStrictCorrection(User user,
                                           CorrectionType correctionType,
                                           String description,
                                           Long productId ,
                                           Integer amount) throws ImpossibleEntitySaveUpdateException, ImpossibleRestUpdateEntityException, ImpossibleAmountDecreasingException {

        Correction correction = new Correction();

        correction.setCorrectionType(correctionType);

        correction.setDescription(description);

        correction.setUser(user);

        List<ProductLine> productLines = new ArrayList<>();

        if (productId != null && amount != null) {
            if (productId <= 0 || amount < 0)
                throw new ImpossibleEntitySaveUpdateException("Неправильній Id продукта или количество меньше нуля");
        } else throw new ImpossibleEntitySaveUpdateException("Отсутсвтует продукт или кол-во");


        ProductLine newProductLine = new ProductLine();
        Optional<Product> prodForProdLineOpt = productDao.findById(productId);


        if (prodForProdLineOpt.isPresent()) {

            Product prodForProdLine = prodForProdLineOpt.get();

            newProductLine.setProduct(prodForProdLine);


            if (prodForProdLine.getAmount() > amount) {
                correction.setType(ProductManipulationType.DEMAND);
                newProductLine.setAmount(prodForProdLine.getAmount() - amount);
                newProductLine.setManipulationMomentAmount(prodForProdLine.getAmount());
            } else if (prodForProdLine.getAmount() < amount) {
                correction.setType(ProductManipulationType.SUPPLY);
                newProductLine.setAmount(amount - prodForProdLine.getAmount());
                newProductLine.setManipulationMomentAmount(prodForProdLine.getAmount());
            } else if (prodForProdLine.getAmount() == amount) return null;

            productLines.add(newProductLine);

        } else throw new ImpossibleEntitySaveUpdateException("Нет такого товара с Id: " + productId);


        validateProductAmountInProductLines(productLines, correction.getType());

        correction.setProductLines(productLines);

        Correction correctionToRet = correctionDao.save(correction);


        List<Product> list = correctionToRet.getProductLines().stream().map(ProductLine::getProduct).collect(Collectors.toList());


        productsBitrixRestSynchronizer.synchronizeProducts(list);

        return correctionToRet;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public List<Correction> saveAllWithRelativeCorrection(User user,
                                                          CorrectionType correctionType,
                                                          String description,
                                                          List<ProductManipulationLine> productManipulationLines) throws ImpossibleEntitySaveUpdateException, ImpossibleRestUpdateEntityException, ImpossibleAmountDecreasingException {

        if (Objects.isNull(productManipulationLines) || productManipulationLines.size() == 0)
            return Collections.emptyList();

        List<Correction> corrections = new ArrayList<>();


        for (ProductManipulationLine prodManLine : productManipulationLines) {

            Long productId = prodManLine.getProductLine().getProduct().getId();

            Integer amount = prodManLine.getProductLine().getAmount();

            Correction correction = new Correction();

            correction.setCorrectionType(correctionType);

            correction.setDescription(description);

            correction.setUser(user);

            List<ProductLine> productLines = new ArrayList<>();

            if (productId != null && amount != null) {
                if (productId <= 0 || amount < 0)
                    throw new ImpossibleEntitySaveUpdateException("Неправильній Id продукта или количество меньше нуля");
            } else throw new ImpossibleEntitySaveUpdateException("Отсутсвтует продукт или кол-во");


            ProductLine newProductLine = new ProductLine();

            Optional<Product> prodForProdLineOpt = productDao.findById(productId);


            if (prodForProdLineOpt.isPresent()) {

                Product prodForProdLine = prodForProdLineOpt.get();

                newProductLine.setProduct(prodForProdLine);


                if (prodManLine.getProductManipulationType() == ProductManipulationType.DEMAND) {
                    correction.setType(ProductManipulationType.DEMAND);
                    newProductLine.setAmount(amount);
                    newProductLine.setManipulationMomentAmount(prodForProdLine.getAmount());
                } else if (prodManLine.getProductManipulationType() == ProductManipulationType.SUPPLY) {
                    correction.setType(ProductManipulationType.SUPPLY);
                    newProductLine.setAmount(amount);
                    newProductLine.setManipulationMomentAmount(prodForProdLine.getAmount());
                } else if (amount == 0) continue;

                productLines.add(newProductLine);

            } else throw new ImpossibleEntitySaveUpdateException("Нет такого товара с Id: " + productId);


            validateProductAmountInProductLines(productLines, correction.getType());

            correction.setProductLines(productLines);

            corrections.add(correction);

        }

        List<Correction> correctionsToRet = correctionDao.saveAll(corrections);

        List<Product> productListToBitrixSync = new ArrayList<>();

        for (Correction corr : correctionsToRet) {
            productListToBitrixSync.addAll(corr.getProductLines().stream().map(ProductLine::getProduct).collect(Collectors.toList()));
        }

        productsBitrixRestSynchronizer.synchronizeProducts(productListToBitrixSync);


        return correctionsToRet;
    }

    @Override
    public Optional<Correction> findById(Long id) {
        return correctionDao.findById(id);
    }

    private void validateProductAmountInProductLines(List<ProductLine> productLines, ProductManipulationType productManipulationType) throws ImpossibleAmountDecreasingException {
        switch (productManipulationType) {

            case SUPPLY:
                for (ProductLine productLineForVal : productLines) {
                    productLineForVal.getProduct().increaseAmount(productLineForVal.getAmount());
                }
                break;
            case DEMAND:
                for (ProductLine productLineForVal : productLines) {
                    productLineForVal.getProduct().decreaseAmount(productLineForVal.getAmount());
                }
                break;
        }
    }
}
