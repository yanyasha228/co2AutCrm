package com.co2AutomaticCrm.RestDao.RestBitrixDao;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRowRestBitrixDto;

import java.util.List;

public interface ProductRowRestBitrixDao {

    List<ProductRowRestBitrixDto> getByDealId(Long id);

    boolean update(Long bitrixDealId, List<ProductRowRestBitrixDto> productRowList);


}
