package com.co2AutomaticCrm.SyncUtils;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.RestServices.PromRestServices.ProductRestPromService;
import com.co2AutomaticCrm.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductsPromRestSynchronizerImpl implements ProductsPromRestSynchronizer {


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRestPromService productRestPromService;


//    public List<Product> synchronizeDbProductsWithRestApiModels() {
//
//        try {
//
//            return synchronizeProducts();
//        } catch (InterruptedException e) {
//
//        }
//
//        return Collections.emptyList();
//    }

    public Product synchronizeOne(Product productForSync) throws ImpossibleEntitySaveUpdateException {

        return productService.save(productRestPromService.getProductById(productForSync.getId()).orElse(null));

    }


    public List<Product> synchronizeAll() throws InterruptedException {

        return productService.save(productRestPromService.getAll());

    }
}
