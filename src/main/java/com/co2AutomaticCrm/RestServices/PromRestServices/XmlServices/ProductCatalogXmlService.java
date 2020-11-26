package com.co2AutomaticCrm.RestServices.PromRestServices.XmlServices;

import com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto.CatalogXmlPromDto;

import java.util.Optional;

public interface ProductCatalogXmlService {

    Optional<CatalogXmlPromDto> getCatalog(String url);

}
