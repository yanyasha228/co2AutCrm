package com.co2AutomaticCrm.ReceiptUtils;

import javax.print.PrintException;
import java.io.FileNotFoundException;

public interface ReceiptPrintService {
    void printPDF(String pathToPdf) throws FileNotFoundException, PrintException;
}
