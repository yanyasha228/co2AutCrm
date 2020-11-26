package com.co2AutomaticCrm.Handlers.HandlersUtils;

import com.co2AutomaticCrm.Models.AppSettings;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;
import com.co2AutomaticCrm.Models.ModelEnums.Currency;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Services.AppSettingsService;
import com.co2AutomaticCrm.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Component
public class BitrixDealWholeSaleProductRowsRecounterImpl implements BitrixDealWholeSaleProductRowsRecounter {

    @Autowired
    private ProductService productService;

    @Autowired
    private AppSettingsService appSettingsService;

    @Override
    public List<BitrixProductRow> recount(List<BitrixProductRow> productRows) {

        if (Objects.isNull(productRows) || productRows.isEmpty()) return Collections.emptyList();

        return productRows.stream().map(productRow -> {

            Optional<Product> productFrDbOpt = productService.findByBitrixId(productRow.getProductBitrixId());

            if (productFrDbOpt.isPresent() && productFrDbOpt.get().getWholeSalePrice() > 0) {

                Product product = productFrDbOpt.get();

                productRow.setDiscount(product.getPrice() - product.getWholeSalePrice());

                productRow.setPrice(product.getWholeSalePrice());

                productRow.setSumPrice(product.getPrice() * productRow.getQuantity());

                productRow.setSumDiscount(productRow.getDiscount() * productRow.getQuantity());

                if (product.getCurrency() != Currency.UAH) {
                    validateProductRowCurrency(productRow, product);
                }

                return productRow;
            }
            return productRow;
        }).collect(Collectors.toList());

    }

    private void validateProductRowCurrency(BitrixProductRow productRow, Product product) {

        Optional<AppSettings> appSettingsOpt = appSettingsService.getSettings();

        if (appSettingsOpt.isPresent()) {

            AppSettings appSettings = appSettingsOpt.get();

            switch (product.getCurrency()) {

                case EUR:
                    productRow.setPrice((productRow.getPrice() * appSettings.getEurCurrency()));
                    productRow.setSumPrice((productRow.getSumPrice() * appSettings.getEurCurrency()));
                    productRow.setDiscount((productRow.getDiscount() * appSettings.getEurCurrency()));
                    productRow.setSumDiscount((productRow.getSumDiscount() * appSettings.getEurCurrency()));

                    break;

                case USD:
                    productRow.setPrice((productRow.getPrice() * appSettings.getUsdCurrency()));
                    productRow.setSumPrice((productRow.getSumPrice() * appSettings.getUsdCurrency()));
                    productRow.setDiscount((productRow.getDiscount() * appSettings.getUsdCurrency()));
                    productRow.setSumDiscount((productRow.getSumDiscount() * appSettings.getUsdCurrency()));

                    break;
            }

        }

    }


}
