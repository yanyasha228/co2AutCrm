package com.co2AutomaticCrm.Validators;

import com.co2AutomaticCrm.Models.AppSettings;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRestBitrixDto;
import com.co2AutomaticCrm.Models.ModelEnums.Currency;
import com.co2AutomaticCrm.Services.AppSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PriceCurrencyBitrixDtoValidatorImpl implements PriceCurrencyBitrixDtoValidator {


    @Autowired
    private AppSettingsService appSettingsService;


    @Override
    public ProductRestBitrixDto convertProductDtoCurrencyToUAH(ProductRestBitrixDto productRestBitrixDto) {

        if (productRestBitrixDto.getCurrency() == Currency.UAH) return productRestBitrixDto;

        Optional<AppSettings> appSettingsOpt = appSettingsService.getSettings();

        if (appSettingsOpt.isPresent()) {
            AppSettings appSettings = appSettingsOpt.get();

            switch (productRestBitrixDto.getCurrency()) {

                case EUR:
                    productRestBitrixDto.setPrice((productRestBitrixDto.getPrice() * appSettings.getEurCurrency()));
                    productRestBitrixDto.setWholeSalePrice((productRestBitrixDto.getWholeSalePrice() * appSettings.getEurCurrency()));
                    productRestBitrixDto.setCurrency(Currency.UAH);
                    break;

                case USD:
                    productRestBitrixDto.setPrice((productRestBitrixDto.getPrice() * appSettings.getUsdCurrency()));
                    productRestBitrixDto.setWholeSalePrice((productRestBitrixDto.getWholeSalePrice() * appSettings.getUsdCurrency()));
                    productRestBitrixDto.setCurrency(Currency.UAH);
                    break;
            }

        }


        return productRestBitrixDto;
    }

}
