package com.co2AutomaticCrm.Models.Dto.XmlDto.PromXmlDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.NONE)
public class CategoryPromXmlDto {

    @XmlAttribute(name = "id")
    private int id;

    @XmlAttribute(name = "parentId")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer parentId;

    @XmlValue
    private String name;
}
