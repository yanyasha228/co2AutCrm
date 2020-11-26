package com.co2AutomaticCrm.RestDao.RestPromDao;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestPromDto.ProductRestPromDto;

import java.util.List;
import java.util.Optional;

public interface ProductRestPromDao {

    Optional<ProductRestPromDto> getProductById(Long id);

    List<ProductRestPromDto> getProductsByGroupId(int id);

    List<ProductRestPromDto> postProducts(List<ProductRestPromDto> productDtos);
}
