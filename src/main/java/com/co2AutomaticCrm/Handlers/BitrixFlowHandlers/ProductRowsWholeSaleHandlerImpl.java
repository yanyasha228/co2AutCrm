package com.co2AutomaticCrm.Handlers.BitrixFlowHandlers;

import com.co2AutomaticCrm.Handlers.HandlersUtils.BitrixDealWholeSaleProductRowsRecounter;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.RestServices.BitrixRestServices.ProductRowRestBitrixService;
import com.co2AutomaticCrm.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductRowsWholeSaleHandlerImpl implements ProductRowsWholeSaleHandler {

    @Autowired
    private BitrixDealWholeSaleProductRowsRecounter bitrixDealWholeSaleProductRowsRecounter;

    @Autowired
    private ProductRowRestBitrixService productRowRestBitrixService;

    @Autowired
    private ProductService productService;

    @Override
    public void handle(BitrixDeal bitrixDeal) throws EntityInconsistencyException {

        if (bitrixDeal.getContact().isWholesaleFlag()) {
            if (!checkProductsWholeSalePriceValidity(bitrixDeal.getProductRows())) {
                bitrixDeal.setProductRows(bitrixDealWholeSaleProductRowsRecounter.recount(bitrixDeal.getProductRows()));
                productRowRestBitrixService.update(bitrixDeal);
            }
            bitrixDeal.setWholeSalePriceCalcFlag(true);
        } else {
            bitrixDeal.setWholeSalePriceCalcFlag(false);
        }

    }

    private boolean checkProductsWholeSalePriceValidity(List<BitrixProductRow> productRows) throws EntityInconsistencyException {

        ////
        for (BitrixProductRow bitrixProductRow :
                productRows) {
            Optional<Product> productForValOpt = productService.findByBitrixId(bitrixProductRow.getProductBitrixId());
            if (!productForValOpt.isPresent())
                throw new EntityInconsistencyException("No such product : " + bitrixProductRow.getProductName());
            if (productForValOpt.get().getWholeSalePrice() != bitrixProductRow.getPrice()) return false;

        }

        return true;
    }
}
