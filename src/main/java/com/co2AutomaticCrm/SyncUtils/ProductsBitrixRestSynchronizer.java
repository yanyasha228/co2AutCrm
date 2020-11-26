package com.co2AutomaticCrm.SyncUtils;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.Models.Product;

import java.util.List;

public interface ProductsBitrixRestSynchronizer {

    Product synchronizeOne(Product productForSync) throws ImpossibleEntitySaveUpdateException, ImpossibleRestUpdateEntityException;

    List<Product> synchronizeProducts(List<Product> productList) throws ImpossibleRestUpdateEntityException;

    List<Product> synchronizeAll() throws ImpossibleRestUpdateEntityException;

}
