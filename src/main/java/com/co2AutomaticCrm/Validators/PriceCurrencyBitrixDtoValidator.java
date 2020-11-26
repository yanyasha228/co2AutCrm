package com.co2AutomaticCrm.Validators;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRestBitrixDto;

public interface PriceCurrencyBitrixDtoValidator {

    ProductRestBitrixDto convertProductDtoCurrencyToUAH(ProductRestBitrixDto productRestBitrixDto);
}
