package com.co2AutomaticCrm.RestDao.RestBitrixDao;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRestBitrixDto;

import java.util.List;
import java.util.Optional;

public interface ProductRestBitrixDao {

    Optional<ProductRestBitrixDto> post(ProductRestBitrixDto productRestBitrixDto);

    Optional<ProductRestBitrixDto> update(ProductRestBitrixDto productRestBitrixDto) throws ImpossibleRestUpdateEntityException;

    List<ProductRestBitrixDto> updateAll(List<ProductRestBitrixDto> productRestBitrixDtos) throws ImpossibleRestUpdateEntityException;

    Optional<ProductRestBitrixDto> getByBitrixId(Long id);

    List<ProductRestBitrixDto> getAll();

}
