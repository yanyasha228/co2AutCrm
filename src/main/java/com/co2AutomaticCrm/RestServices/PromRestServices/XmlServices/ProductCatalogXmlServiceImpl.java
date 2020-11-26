package com.co2AutomaticCrm.RestServices.PromRestServices.XmlServices;

import com.co2AutomaticCrm.HelpUtils.DataManipulationHelpers.ProductsCatalogPromXmlUnmarshaller;
import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.CatalogXmlPromDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.bind.JAXBException;
import java.nio.charset.Charset;
import java.util.Optional;

@Service
public class ProductCatalogXmlServiceImpl implements ProductCatalogXmlService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<CatalogXmlPromDto> getCatalog(String syncUrl) {

        ProductsCatalogPromXmlUnmarshaller productsPromXmlUnmarshaller;

        CatalogXmlPromDto catalogPromDto = null;

        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));

        HttpEntity<String> entity = new HttpEntity<String>(new HttpHeaders());

        ResponseEntity<String> response = restTemplate.exchange(syncUrl, //
                HttpMethod.GET, entity, String.class);

        HttpHeaders headers = response.getHeaders();


        if (headers.get("Content-Type").contains("text/xml") && response.getStatusCode().is2xxSuccessful()) {

            productsPromXmlUnmarshaller = new ProductsCatalogPromXmlUnmarshaller(response.getBody().replaceAll("<!DOCTYPE yml_catalog SYSTEM \"shops.dtd\">", ""));

            try {
                catalogPromDto = productsPromXmlUnmarshaller.buildXml().getCatalog();
            } catch (JAXBException e) {
                e.printStackTrace();
            }


        }
        return Optional.ofNullable(catalogPromDto);
    }

}
