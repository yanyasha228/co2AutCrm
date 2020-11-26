package com.co2AutomaticCrm.Controllers.RozetkaControllers;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ProductXmlCatalogInaccessibilityException;
import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.CatalogXmlPromDto;
import com.co2AutomaticCrm.SyncUtils.RozetkaCatalogXmlSynchronizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rozetka")
public class RozetkaController {

    @Autowired
    private RozetkaCatalogXmlSynchronizer rozetkaCatalogXmlSynchronizer;


    @ResponseBody
    @RequestMapping(value = "/catalog.xml", produces = MediaType.APPLICATION_XML_VALUE)
    public CatalogXmlPromDto rozetkaCatalog() throws ProductXmlCatalogInaccessibilityException {

        return rozetkaCatalogXmlSynchronizer.unmarshalSavedCatalog();

    }

}
