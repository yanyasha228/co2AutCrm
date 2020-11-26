package com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement(name = "offer")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductXmlPromDto {

    @XmlAttribute(name = "id")
    private long id;

    @XmlAttribute(name = "available")
    private boolean available;

    @XmlElement(name = "stock_quantity")
    private int amount = 10;

    @XmlElement(name = "categoryId")
    private int categoryId;

    @XmlElement(name = "price")
    private float price;

    @XmlElement(name = "url")
    private String url;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "currencyId")
    private String currency;

    @XmlElement(name = "picture")
    private List<String> pictures;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "vendor")
    private String vendor;

    @XmlElement(name = "country_of_origin")
    private String countryOfOrigin;

    @XmlElement(name = "pickup")
    private String pickup;

    @XmlElement(name = "param")
    private List<ProductParamXmlPromDto> params = new ArrayList<>();

}
