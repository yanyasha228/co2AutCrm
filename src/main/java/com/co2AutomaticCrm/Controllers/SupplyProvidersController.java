package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Models.SupplyProvider;
import com.co2AutomaticCrm.Services.SupplyProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@PreAuthorize("hasAuthority('STOREKEEPER')")
@RequestMapping("/supplies/providers")
public class SupplyProvidersController {


    @Autowired
    private SupplyProviderService supplyProviderService;


    @GetMapping
    public String supplyProvidersList(Model model,
                                      @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable,
                                      @RequestParam Optional<String> nonFullSupplyProviderName) {


        Page<SupplyProvider> supplyProviderPage = supplyProviderService.
                findSupplyProvidersWithPagination(nonFullSupplyProviderName.orElse(""),
                        pageable);


        model.addAttribute("nonFullSupplyProviderName", nonFullSupplyProviderName.orElse(""));


        model.addAttribute("supplyProvidersPage",
                supplyProviderPage);


        return "supplyProviders";


    }

}
