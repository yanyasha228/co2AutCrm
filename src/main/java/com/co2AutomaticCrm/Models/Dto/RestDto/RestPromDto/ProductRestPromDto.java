package com.co2AutomaticCrm.Models.Dto.RestDto.RestPromDto;

import com.co2AutomaticCrm.Models.Group;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Currency;
import java.util.Objects;

@Data
@NoArgsConstructor
public class ProductRestPromDto {

    private long id;

    private String name;

    private float price;

    @JsonIgnore
    private Group group;

    private String main_image;

    private Currency currency;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRestPromDto that = (ProductRestPromDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
