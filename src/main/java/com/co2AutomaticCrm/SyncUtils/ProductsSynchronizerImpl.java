package com.co2AutomaticCrm.SyncUtils;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductsSynchronizerImpl implements ProductsSynchronizer {

    @Autowired
    private ProductsPromRestSynchronizer productsPromRestSynchronizer;

    @Autowired
    private ProductsBitrixRestSynchronizer productsBitrixRestSynchronizer;

    @Override
    public void sync() {

        try {
            productsPromRestSynchronizer.synchronizeAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            productsBitrixRestSynchronizer.synchronizeAll();
        } catch (ImpossibleRestUpdateEntityException e) {
            e.printStackTrace();
        }

    }
}
