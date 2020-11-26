package com.co2AutomaticCrm.ReceiptUtils;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.itextpdf.text.DocumentException;

import javax.print.PrintException;
import java.io.IOException;

public interface DealReceiptService {

    void print(BitrixDeal bitrixDeal) throws IOException, DocumentException, PrintException;

}
