package com.co2AutomaticCrm.SyncUtils;

import com.co2AutomaticCrm.Models.Product;

import java.util.List;

public interface ProductsPromXmlSynchronizer {

    List<Product> syncProductsWithPromXml(String syncUrl);
}
