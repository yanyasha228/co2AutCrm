package com.co2AutomaticCrm.FilesUtils;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.*;
import com.co2AutomaticCrm.Models.Correction;
import com.co2AutomaticCrm.Models.HelpModels.ProductManipulationLine;
import com.co2AutomaticCrm.Models.ModelEnums.CorrectionType;
import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Models.ProductLine;
import com.co2AutomaticCrm.Models.User;
import com.co2AutomaticCrm.Services.CorrectionService;
import com.co2AutomaticCrm.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Component
public class ProductStoreCsvCorrectionsUploaderImpl implements ProductStoreCsvCorrectionsUploader {

    @Autowired
    private CorrectionService correctionService;

    @Autowired
    private ProductService productService;

    private static final int OPERATION_DISC_INDEX = 3;
    private static final int CORRECTION_AMOUNT_INDEX = 4;
    private static final int PRODUCT_ID_INDEX = 5;

    @Override
    public List<Correction> doCorrections(String fileName, User user) throws FileNotFoundException,
            ImpossibleEntitySaveUpdateException,
            ImpossibleAmountDecreasingException,
            ImpossibleRestUpdateEntityException,
            IncorrectFileException,
            CsvCorrectionResultException {

        List<List<String>> records = getRecordsFromCsvFile(fileName);

        return correctionService.saveAllWithRelativeCorrection(user, CorrectionType.CSV, "", mapRecordsToProductManipulationLines(records));

    }

    private List<ProductManipulationLine> mapRecordsToProductManipulationLines(List<List<String>> records) throws IncorrectFileException,
            CsvCorrectionResultException {

        List<ProductManipulationLine> productManipulationLines = new ArrayList<>();
        CsvCorrectionResultException csvCorrectionResultException = new CsvCorrectionResultException("Ошибки при коррекции");

        int recordNumber = 1;
        for (List<String> recs : records) {
            if (checkIfRecordIsSuitableForCorrectionAndRemovePlaces(recs)) {
                Optional<Product> productOpt = productService.findById(Long.valueOf(recs.get(PRODUCT_ID_INDEX)));
                if (productOpt.isPresent()) {
                    if (getManipulationTypeFromRecord(recs) == ProductManipulationType.DEMAND && (productOpt.get().getAmount() - Integer.valueOf(recs.get(CORRECTION_AMOUNT_INDEX)) < 0)) {
                        csvCorrectionResultException.getExceptions().add(new CsvProductCorrectionException(productOpt.get(), new InsufficientProductAmountException("Недостаточное количество товара"), "Ошибка при попытке списать " + recs.get(CORRECTION_AMOUNT_INDEX) + " шт." + " | " + "Строка : №" + recordNumber));
                    } else {
                        productManipulationLines.add(new ProductManipulationLine(getManipulationTypeFromRecord(recs), new ProductLine(productOpt.get(), Integer.valueOf(recs.get(CORRECTION_AMOUNT_INDEX)))));
                    }
                } else
                    throw new IncorrectFileException("Не существует такого товара с ID : " + recs.get(PRODUCT_ID_INDEX));
            }
//            } else throw new IncorrectFileException("Погрешность в заполнении файла");
            recordNumber++;
        }
        if (csvCorrectionResultException.getExceptions().size() > 0) throw csvCorrectionResultException;

        return productManipulationLines;
    }

    private ProductManipulationType getManipulationTypeFromRecord(List<String> record) {

        String operationDiscStr = record.get(OPERATION_DISC_INDEX).replaceAll("\\s+", "");

        switch (operationDiscStr) {
            case "+":
                return ProductManipulationType.SUPPLY;
            case "-":
                return ProductManipulationType.DEMAND;
        }

        return null;

    }

    private boolean checkIfRecordIsSuitableForCorrectionAndRemovePlaces(List<String> record) {

        if(record.size()>=6) {
            String operationDiscStr = record.get(OPERATION_DISC_INDEX).replaceAll("\\s+", "");
            String correctionAmountStr = record.get(CORRECTION_AMOUNT_INDEX).replaceAll("\\s+", "");
            String productIdStr = record.get(PRODUCT_ID_INDEX).replaceAll("\\s+", "");

            if (isNumber(correctionAmountStr) && isNumber(productIdStr) && isOperationDisc(operationDiscStr)) {
                record.set(OPERATION_DISC_INDEX, operationDiscStr);
                record.set(CORRECTION_AMOUNT_INDEX, correctionAmountStr);
                record.set(PRODUCT_ID_INDEX, productIdStr);
                return true;
            }
        }
        return false;

    }

    private boolean isOperationDisc(String operatorString) {
        return operatorString.equalsIgnoreCase("+") || operatorString.equalsIgnoreCase("-");
    }

    private boolean isNumber(String numberString) {
        try {
            Long.valueOf(numberString);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    private List<List<String>> getRecordsFromCsvFile(String fileName) throws FileNotFoundException {

        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName));) {
            while (scanner.hasNextLine()) {

                records.add(getRecordFromLine(scanner.nextLine()));

            }
        }

        return records;
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }
}
