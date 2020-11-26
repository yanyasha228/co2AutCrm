package com.co2AutomaticCrm.Handlers.HandlersUtils;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BitrixDealSalaryCounterImpl implements BitrixDealSalaryCounter {

    private final static int MANAGER_PERCENT = 1;
    //    ALL PRODUCTS in that group add 50 grn bonus
    private final static int REPAIRS_GROUP_ID = 30114118;

    //  that product adds 100 grn bonus
    private final static int MR_TUN_ID = 697687786;


    @Autowired
    private ProductService productService;


    @Override
    public float count(BitrixDeal bitrixDeal) {

        float salaryIncrease = 0;

        int productRowsAmount = bitrixDeal.getProductRows().size();

        if (bitrixDeal.getSumPrice() <= 1000) {
            salaryIncrease = 10;
        } else salaryIncrease = (bitrixDeal.getSumPrice() / 100) * MANAGER_PERCENT;

        if (productRowsAmount >= 6) {
            if (productRowsAmount == 6 && salaryIncrease < 20) salaryIncrease = 20;
            if (productRowsAmount > 6 && salaryIncrease < 50) salaryIncrease = 50;
        }

        salaryIncrease += countSalaryProductsBonus(bitrixDeal.getProductRows());

        if (bitrixDeal.getContact().isWholesaleFlag()) salaryIncrease += 10;

        return salaryIncrease;

    }


    private float countSalaryProductsBonus(List<BitrixProductRow> productRows) {

        float sumBonus = 0;

        for (BitrixProductRow prodRow :
                productRows) {
            Optional<Product> productOpt = productService.findByBitrixId(prodRow.getProductBitrixId());
            if (productOpt.isPresent()) {

                Product product = productOpt.get();

                if (product.getGroup().getId() == REPAIRS_GROUP_ID) {
                    if (product.getId() == MR_TUN_ID) {
                        sumBonus += 100;
                    } else sumBonus += 50;


                }

            }
        }
        return sumBonus;
    }

}
