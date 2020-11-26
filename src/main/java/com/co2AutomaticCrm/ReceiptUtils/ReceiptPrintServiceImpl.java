package com.co2AutomaticCrm.ReceiptUtils;

import org.springframework.stereotype.Component;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Component
public class ReceiptPrintServiceImpl implements ReceiptPrintService {

    @Override
    public void printPDF(String pathToPdf) throws FileNotFoundException, PrintException {
        System.out.println("Start Printing inside ReceiptPrintServiceImpl");
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        System.out.println(printService.getName());
        DocPrintJob job = printService.createPrintJob();
        job.addPrintJobListener(new PrintJobAdapter() {
            @Override
            public void printDataTransferCompleted(PrintJobEvent printJobEvent) {
                super.printDataTransferCompleted(printJobEvent);
            }

        });
        FileInputStream fis = new FileInputStream(pathToPdf);


        Doc doc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.PDF, null);
        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();
        attributeSet.add(new Copies(1));
        job.print(doc, attributeSet);
    }
}
