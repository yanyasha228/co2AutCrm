package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Models.Product;
import com.co2AutomaticCrm.Services.GroupService;
import com.co2AutomaticCrm.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductsController {

//    private static final Logger logger = Logger.getLogger(ProductsController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private GroupService groupService;

    @GetMapping
    public String productsList(Model model,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 50) Pageable pageable,
                               @RequestParam Optional<Boolean> availability,
                               @RequestParam Optional<Integer> groupId,
                               @RequestParam Optional<String> nonFullProductName) {


        Page<Product> productsPage = productService.findProductsWithPagination(nonFullProductName.orElse(""),
                groupService.findById(groupId.orElse(0)).orElse(null),
                availability.orElse(null),
                pageable);


        model.addAttribute("availability", availability.orElse(null));

        model.addAttribute("groupId", groupId.orElse(0));

        model.addAttribute("nonFullProductName", nonFullProductName.orElse(""));

        model.addAttribute("productsPage",
                productsPage);


        model.addAttribute("groups", groupService.findAll());

        return "products";

    }
}
