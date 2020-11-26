package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Models.SupplyProvider;
import com.co2AutomaticCrm.Services.SupplyProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('STOREKEEPER')")
@RequestMapping("/supplies/providers/create")
public class SupplyProvidersCreateController {

    @Autowired
    private SupplyProviderService supplyProviderService;

//    private static final Logger logger = Logger.getLogger(OrdersEditController.class);

    @GetMapping
    public String createSupplyProvider(Model model) {

        model.addAttribute("supplyProvider", new SupplyProvider());

        return "supplyProvidersCreate";
    }

    @PostMapping("submit")
    public String createSupplyProviderSubmit(@ModelAttribute SupplyProvider supplyProvider) {

        supplyProviderService.save(supplyProvider);

        return "redirect:/supplies/providers";

    }
}
