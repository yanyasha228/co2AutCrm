package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Models.HelpModels.GenProductManipulation;
import com.co2AutomaticCrm.Models.ModelEnums.ProductManipulationType;
import com.co2AutomaticCrm.Services.GenProductManipulationService;
import com.co2AutomaticCrm.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products/{id}/history")
public class ProductHistoryController {

    @Autowired
    private GenProductManipulationService manipulationService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String getHistory(Model model, @PageableDefault(size = 50) Pageable pageable,
                             @PathVariable Long id,
                             @RequestParam(required = false) ProductManipulationType type) {


        Page<GenProductManipulation> manipulationPage = manipulationService.findAllWithPagination(id, type, pageable);

        model.addAttribute("manipulationPage", manipulationPage);
        model.addAttribute("product", productService.findById(id).orElse(null));
        model.addAttribute("id", id);

        model.addAttribute("type", type);


        return "productHistory";
    }
}
