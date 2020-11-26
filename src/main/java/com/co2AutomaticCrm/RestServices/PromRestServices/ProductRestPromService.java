package com.co2AutomaticCrm.RestServices.PromRestServices;

import com.co2AutomaticCrm.Models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRestPromService {

    Optional<Product> getProductById(Long id);

    List<Product> getAll() throws InterruptedException;

    List<Product> getProductsByGroupId(int id);

    List<Product> postProducts(List<Product> productList);

}
