package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestPromModels;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EditProductsPromRestResponse {

    private List<Integer> processed_ids;

    private ErrorsPromRestResponse errors;
}
