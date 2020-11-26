package com.co2AutomaticCrm.RestServices.BitrixRestServices;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.Models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRestBitrixService {

    List<Product> getAll();

    Optional<Product> getByBitrixId(Long bitrix_id);

    Optional<Product> update(Product product) throws ImpossibleRestUpdateEntityException;

    List<Product> updateAll(List<Product> productList) throws ImpossibleRestUpdateEntityException;


}
