package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestPromModels;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestPromDto.ProductRestPromDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Data
@NoArgsConstructor
public class ProductPromRestResponse {

    private ProductRestPromDto product;

    private ErrorsPromRestResponse errors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPromRestResponse that = (ProductPromRestResponse) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, errors);
    }
}
