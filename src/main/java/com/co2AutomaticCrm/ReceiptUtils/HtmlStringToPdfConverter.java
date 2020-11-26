package com.co2AutomaticCrm.ReceiptUtils;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

public interface HtmlStringToPdfConverter {
    String convert(String htmlString, String lablePath) throws IOException, DocumentException;
}
