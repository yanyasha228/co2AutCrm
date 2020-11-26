package com.co2AutomaticCrm.SyncUtils;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ProductXmlCatalogInaccessibilityException;
import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.CatalogXmlPromDto;

public interface RozetkaCatalogXmlSynchronizer {

    CatalogXmlPromDto generate() throws ProductXmlCatalogInaccessibilityException;

    void generateAndSave() throws ProductXmlCatalogInaccessibilityException;

    CatalogXmlPromDto unmarshalSavedCatalog();

}
