package com.co2AutomaticCrm.SyncUtils;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.Models.Product;

import java.util.List;

public interface ProductsPromRestSynchronizer {

    Product synchronizeOne(Product productForSync) throws ImpossibleEntitySaveUpdateException;

    List<Product> synchronizeAll() throws InterruptedException;
}
