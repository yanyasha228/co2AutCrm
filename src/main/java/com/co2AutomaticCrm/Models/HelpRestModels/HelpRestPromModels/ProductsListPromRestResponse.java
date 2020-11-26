package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestPromModels;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestPromDto.ProductRestPromDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
@NoArgsConstructor
public class ProductsListPromRestResponse {

    private List<ProductRestPromDto> products = new ArrayList<ProductRestPromDto>();

    private int group_id;

    private ErrorsPromRestResponse errors;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductsListPromRestResponse that = (ProductsListPromRestResponse) o;
        return group_id == that.group_id &&
                Objects.equals(products, that.products) &&
                Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, group_id, errors);
    }
}
