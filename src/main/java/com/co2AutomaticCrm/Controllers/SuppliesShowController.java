package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Services.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("supplies/{id}/show")
public class SuppliesShowController {

    @Autowired
    private SupplyService supplyService;

    @GetMapping
    public String showSupply(Model model,
                             @PathVariable Long id) {

        model.addAttribute("supply", supplyService.findById(id).orElse(null));

        return "suppliesShow";
    }

}
