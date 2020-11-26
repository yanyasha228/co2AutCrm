package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Models.SupplyProvider;
import com.co2AutomaticCrm.Services.SupplyProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasAuthority('STOREKEEPER')")
@RequestMapping("/supplies/providers/{id}/edit")
public class SupplyProvidersEditController {

    @Autowired
    private SupplyProviderService supplyProviderService;

    @GetMapping
    private String supplyProviderEdit(Model model,
                                      @PathVariable Integer id) {

        model.addAttribute("supplyProvider", supplyProviderService.findById(id).orElse(null));

        return "supplyProvidersEdit";
    }

    @PostMapping("/submit")
    private String supplyProviderEditSubmit(@ModelAttribute SupplyProvider supplyProvider) {

//        supplyProvider.
//                setCreationDate(supplyProviderService.findById(supplyProvider.getId()).get().getCreationDate());

        supplyProviderService.save(supplyProvider);

        return "redirect:/supplies/providers";
    }

}
