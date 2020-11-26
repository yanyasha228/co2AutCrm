package com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@XmlRootElement(name = "param")
@XmlAccessorType(XmlAccessType.NONE)
public class ProductParamXmlPromDto {

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "unit")
    private String unit;

    @XmlValue
    private String value;


}
