package com.co2AutomaticCrm.HelpUtils.DataManipulationHelpers;


import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.CatalogXmlPromDto;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;


public class ProductsCatalogPromXmlUnmarshaller {


    private String xmlStringToUnmarshalling;

    private Document document;

    public ProductsCatalogPromXmlUnmarshaller(String xmlStringToUnmarshalling) {

        this.xmlStringToUnmarshalling = xmlStringToUnmarshalling;

    }

    public ProductsCatalogPromXmlUnmarshaller buildXml() {

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(new InputSource(new StringReader(xmlStringToUnmarshalling)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;

    }

    public CatalogXmlPromDto getCatalog() throws JAXBException {


        JAXBContext context = JAXBContext.newInstance(CatalogXmlPromDto.class);

        CatalogXmlPromDto shopPromXmlDto = (CatalogXmlPromDto) (context.createUnmarshaller().unmarshal(document));

        return shopPromXmlDto;
    }

    public Document getDocument() {
        return document;
    }

}
