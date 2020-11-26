package com.co2AutomaticCrm.SyncUtils;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ProductXmlCatalogInaccessibilityException;
import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.CatalogXmlPromDto;
import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.ProductParamXmlPromDto;
import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.ProductXmlPromDto;
import com.co2AutomaticCrm.Models.RozetkaModels.RozetkaProductParam;
import com.co2AutomaticCrm.RestServices.PromRestServices.XmlServices.ProductCatalogXmlService;
import com.co2AutomaticCrm.Services.RozetkaModelServices.RozetkaProductParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RozetkaCatalogXmlSynchronizerImpl implements RozetkaCatalogXmlSynchronizer {

    @Autowired
    private ProductCatalogXmlService productCatalogXmlService;

    @Autowired
    private RozetkaProductParamService productParamService;

    @Value("${rest.prom.xml.catalog}")
    private String xmlUrl;

    @Value("${upload.xmlCatalogRozetka.path}")
    private String xmlFilePath;


    @Override
    public void generateAndSave() throws ProductXmlCatalogInaccessibilityException {

        Optional<CatalogXmlPromDto> catalogPromDtoOpt = productCatalogXmlService.getCatalog(xmlUrl);

        CatalogXmlPromDto catalogPromDto;

        if (catalogPromDtoOpt.isPresent()) {
            catalogPromDto = catalogPromDtoOpt.get();
            validateParameters(catalogPromDto);
            try {
                marshalAndSave(catalogPromDto);
            } catch (JAXBException | IOException e) {
                e.printStackTrace();
            }

        } else throw new ProductXmlCatalogInaccessibilityException("No file on this url");

    }

    @Override
    public CatalogXmlPromDto unmarshalSavedCatalog() {

        JAXBContext jaxbContext = null;
        Unmarshaller unmarshaller = null;
        InputStream inputStream = null;
        CatalogXmlPromDto catalogXmlPromDtoToRet;
        try {
            inputStream = new FileInputStream(xmlFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        try {
            jaxbContext = JAXBContext.newInstance(CatalogXmlPromDto.class);
            unmarshaller = jaxbContext.createUnmarshaller();
            catalogXmlPromDtoToRet = (CatalogXmlPromDto) unmarshaller.unmarshal(inputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }

        return catalogXmlPromDtoToRet;
    }

    @Override
    public CatalogXmlPromDto generate() throws ProductXmlCatalogInaccessibilityException {

        Optional<CatalogXmlPromDto> catalogPromDtoOpt = productCatalogXmlService.getCatalog(xmlUrl);

        CatalogXmlPromDto catalogPromDto;

        if (catalogPromDtoOpt.isPresent()) {
            catalogPromDto = catalogPromDtoOpt.get();
            validateParameters(catalogPromDto);
        } else throw new ProductXmlCatalogInaccessibilityException("No file on this url");

        return catalogPromDto;
    }

    private void validateParameters(CatalogXmlPromDto catalogXmlPromDto) {

        Map<String, Long> paramsMap = productParamService.findAll().stream()
                .collect(Collectors.toMap(RozetkaProductParam::getName, RozetkaProductParam::getId));

        List<ProductXmlPromDto> productList = catalogXmlPromDto.getShopXmlPromDto().getOffersList();

        for (ProductXmlPromDto product :
                productList) {

            List<ProductParamXmlPromDto> validParams = product.getParams().stream()
                    .filter(productParamXmlPromDto ->
                            !Objects.isNull(paramsMap.get(productParamXmlPromDto.getName()))).collect(Collectors.toList());

            product.setParams(validParams);


        }

    }

    private void marshalAndSave(CatalogXmlPromDto catalogXmlPromDto) throws JAXBException, IOException {

        FileWriter fileWriter = new FileWriter(xmlFilePath);

        JAXBContext jaxbContext = JAXBContext.newInstance(CatalogXmlPromDto.class);

        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter stringWriter = new StringWriter();

        marshaller.marshal(catalogXmlPromDto, stringWriter);

        String xmlContent = stringWriter.toString();

        fileWriter.write(xmlContent);

        fileWriter.close();

    }
}
