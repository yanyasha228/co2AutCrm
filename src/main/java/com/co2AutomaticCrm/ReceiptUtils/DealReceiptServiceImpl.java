package com.co2AutomaticCrm.ReceiptUtils;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.Models.NonPersistentModels.Receipt;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.print.PrintException;
import java.io.IOException;

@Service
public class DealReceiptServiceImpl implements DealReceiptService {

    @Value("${receipt.co2Lable.path}")
    private String co2LableUri;

    @Value("${receipt.gtLable.path}")
    private String gtLableUri;

    @Autowired
    private HtmlStringToPdfConverter htmlStringToPdfConverter;

    @Autowired
    private ReceiptPrintService receiptPrintService;

    @Autowired
    private ReceiptBuilder receiptBuilder;

    @Autowired
    private ReceiptToHtmlConverter receiptToHtmlConverter;


    @Override
    public void print(BitrixDeal bitrixDeal) throws IOException, DocumentException, PrintException {

        System.out.println("Start ReceiptBuilder new Receipt");

        Receipt receipt = receiptBuilder.build(bitrixDeal);

        System.out.println("Start receiptToHtmlConverter.convert");

        String htmlReceiptString = receiptToHtmlConverter.convert(receipt);

        String pdfReceiptFileUri;

        System.out.println("Start htmlStringToPdfConverter.convert");
        if (!bitrixDeal.isRozetkaFlag()) {
            pdfReceiptFileUri = htmlStringToPdfConverter.convert(htmlReceiptString, co2LableUri);
        } else pdfReceiptFileUri = htmlStringToPdfConverter.convert(htmlReceiptString, gtLableUri);

        System.out.println("Start receiptPrintService.printPDF");
        receiptPrintService.printPDF(pdfReceiptFileUri);

    }

}
