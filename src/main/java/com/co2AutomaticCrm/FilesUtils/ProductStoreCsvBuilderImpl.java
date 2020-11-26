package com.co2AutomaticCrm.FilesUtils;

import com.co2AutomaticCrm.Models.Group;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Services.GroupService;
import com.co2AutomaticCrm.Services.ProductService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Component
public class ProductStoreCsvBuilderImpl implements ProductStoreCsvBuilder {

    @Autowired
    private ProductService productService;

    @Autowired
    private GroupService groupService;

    private static String[] HEADERS = {"№", "Название", "Кол-во", "Опер." , "Акт.", "ID"};

    @Value("${upload.csvStore.path}")
    private String uploadCsvPath;

    @Override
    public String createCsv(Boolean availability) throws IOException {
        return writeCsv(getActiveProductsStringRepresentation(availability));
    }

    @Override
    public String createCsv(Group group, Boolean availability) throws IOException {
        return writeCsv(getActiveProductsByGroupStringRepresentation(group, availability));
    }

    private String writeCsv(List<List<String>> productsStringRepresentation) throws IOException {

        FileWriter out = new FileWriter(uploadCsvPath);

        try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
            productsStringRepresentation.forEach(list -> {
                try {
                    printer.printRecord(list);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        return uploadCsvPath;
    }

    private List<List<String>> getActiveProductsStringRepresentation(Boolean availability) {

        Map<Group, List<Product>> groupProductMap = new HashMap<>();

        List<Group> allGroups = groupService.findAll();

        for (Group gr : allGroups) {

            List<Product> productsForMap;

            if (Objects.isNull(availability)) {
                productsForMap = productService.findAllByGroup(gr);
            } else {
                productsForMap = productService.findAllByGroupAndAvailability(gr, availability);
            }

            if (productsForMap.size() > 0) {
                productsForMap.sort(Comparator.comparing(Product::getName));
            }

            groupProductMap.put(gr, productsForMap);

        }


        List<List<String>> productFieldRepresentation = new ArrayList<>();

        int number = 1;
        for (Map.Entry<Group, List<Product>> entry : groupProductMap.entrySet()) {
            if (entry.getValue().size() == 0) continue;

            List<String> title = new ArrayList<>();
            title.add("---");
            title.add("---------------------------" + entry.getKey().getName() + "---------------------------");
            title.add("---");
            title.add("---");
            title.add("---");
            title.add("----------------");

            List<String> titleSpacing = new ArrayList<>();
            titleSpacing.add("");
            titleSpacing.add("");
            titleSpacing.add("");
            titleSpacing.add("");
            titleSpacing.add("");
            productFieldRepresentation.add(titleSpacing);
            productFieldRepresentation.add(title);
            productFieldRepresentation.add(titleSpacing);
            for (Product prToRep : entry.getValue()) {
                List<String> listToAdd = new ArrayList<>();
                listToAdd.add(String.valueOf(number));
                listToAdd.add(removeAllExChars(prToRep.getName()));
                listToAdd.add(String.valueOf(prToRep.getAmount()));
                listToAdd.add(" ");
                listToAdd.add(" ");
                listToAdd.add(String.valueOf(prToRep.getId()));
                productFieldRepresentation.add(listToAdd);
                number++;
            }
        }


        return productFieldRepresentation;

    }

    private List<List<String>> getActiveProductsByGroupStringRepresentation(Group group, Boolean availability) {

        List<Product> productList;
        if (Objects.isNull(availability)) {
            productList = productService.findAllByGroup(group);
        } else {
            productList = productService.findAllByGroupAndAvailability(group, availability);
        }

        List<List<String>> productFieldRepresentation = new ArrayList<>();

        if (productList.size() > 0) {
            productList.sort(Comparator.comparing(Product::getName));
        }

        List<String> title = new ArrayList<>();
        title.add("---");
        title.add("---------------------------" + group.getName() + "---------------------------");
        title.add("---");
        title.add("---");
        title.add("---");
        title.add("----------------");
        productFieldRepresentation.add(title);
        int number = 1;
        for (Product prToRep : productList) {
            List<String> listToAdd = new ArrayList<>();
            listToAdd.add(String.valueOf(number));
            listToAdd.add(removeAllExChars(prToRep.getName()));
            listToAdd.add(String.valueOf(prToRep.getAmount()));
            listToAdd.add(" ");
            listToAdd.add(" ");
            listToAdd.add(String.valueOf(prToRep.getId()));
            productFieldRepresentation.add(listToAdd);
            number++;
        }


        return productFieldRepresentation;

    }

    private String removeAllExChars(String str){
        String strToRet = str.replaceAll(",", " ");
        strToRet = strToRet.replaceAll(";" , " ");

        return strToRet;
    }
}
