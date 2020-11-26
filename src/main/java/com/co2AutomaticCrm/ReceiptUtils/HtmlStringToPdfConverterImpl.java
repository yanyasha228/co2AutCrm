package com.co2AutomaticCrm.ReceiptUtils;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.IOException;

@Component
public class HtmlStringToPdfConverterImpl implements HtmlStringToPdfConverter {

    @Value("${receipt.pdf.path}")
    private String pdfReceiptPath;

    @Value("${receipt.font.path}")
    private String receiptFontPath;

    public String convert(String htmlString, String lablePath) throws IOException, DocumentException {

        FileOutputStream outputStream = new FileOutputStream(pdfReceiptPath);

        ITextRenderer renderer = new ITextRenderer();

        ITextFontResolver fontResolver = renderer.getFontResolver();

        renderer.getSharedContext().setReplacedElementFactory(new ProfileImageReplacedElementFactory(renderer.getSharedContext().getReplacedElementFactory(), lablePath));

        renderer.setDocumentFromString(htmlString);


        fontResolver.addFont(receiptFontPath,
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // Создаем writer для записи в pdf


        renderer.layout();

        renderer.createPDF(outputStream);
        outputStream.flush();
        outputStream.close();

        return pdfReceiptPath;

    }

}
