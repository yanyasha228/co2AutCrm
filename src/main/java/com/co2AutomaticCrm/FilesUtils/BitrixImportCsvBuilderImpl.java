package com.co2AutomaticCrm.FilesUtils;

import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Services.ProductService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class BitrixImportCsvBuilderImpl implements BitrixImportCsvBuilder {

    @Autowired
    private ProductService productService;

    private static String[] HEADERS = {"Назва", "Активний", "Валюта", "Ціна", "Одиниця виміру", "Сорт.", "Картинка для анонса", "Детальна картинка", "Количество", "prom_id"};

    @Value("${upload.csvImportFile.path}")
    private String uploadCsvPath;

    public String buildCsvFromNotImportedProducts() throws IOException {

        FileWriter out = new FileWriter(uploadCsvPath);


        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            getProductThatNotImportedFieldsStringRepresentation().forEach(list -> {
                try {
                    printer.printRecord(list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return uploadCsvPath;

    }

    // This method sets flag isActive to default value true("Так")
    private List<List<String>> getProductThatNotImportedFieldsStringRepresentation() {

        List<Product> productList = productService.findAll()
                .stream()
                .filter(product -> product.getBitrixId() == 0)
                .collect(Collectors.toList());
        List<List<String>> productFieldRepresentation = new ArrayList<>();

        for (Product prToRep : productList) {
            List<String> listToAdd = new ArrayList<>();
            listToAdd.add(prToRep.getName());
            listToAdd.add("Так");
            listToAdd.add(prToRep.getCurrency().toString());
            listToAdd.add(String.valueOf(prToRep.getPrice()));
            listToAdd.add("шт.");
            listToAdd.add("100");
            listToAdd.add(prToRep.getMain_image());
            listToAdd.add(prToRep.getMain_image());
            listToAdd.add(String.valueOf(prToRep.getAmount()));
            listToAdd.add(String.valueOf(prToRep.getId()));
            productFieldRepresentation.add(listToAdd);
        }


        return productFieldRepresentation;

    }


}
