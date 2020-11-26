package com.co2AutomaticCrm.SyncUtils;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Services.ProductService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class WholeSalePricesCsvImporterImpl implements WholeSalePricesCsvImporter {
    @Autowired
    private ProductService productService;


    @Override
    public boolean importPrices(String urlForCsvFilePricesList) {

        List<Product> productList = new ArrayList<Product>();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(urlForCsvFilePricesList));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withHeader("Name", "Price", "Currency", "ID", "URL")
                        .withIgnoreHeaderCase().withFirstRecordAsHeader());
        ) {
            for (CSVRecord csvRecord : csvParser) {

                Optional<Product> productOpt = productService.findById(Long.parseLong(csvRecord.get("ID")));
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    product.setWholeSalePrice(Float.parseFloat(csvRecord.get("Price").replaceAll(",", ".")));
                    productService.save(product);
                }
            }
        } catch (IOException | ImpossibleEntitySaveUpdateException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }
}
