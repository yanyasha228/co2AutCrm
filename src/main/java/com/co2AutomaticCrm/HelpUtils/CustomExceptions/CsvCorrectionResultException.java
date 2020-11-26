package com.co2AutomaticCrm.HelpUtils.CustomExceptions;

import com.co2AutomaticCrm.Models.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CsvCorrectionResultException extends Exception {
    public CsvCorrectionResultException(String message) {
        super(message);
    }

    private List<CsvProductCorrectionException> exceptions = new ArrayList<>();
}
