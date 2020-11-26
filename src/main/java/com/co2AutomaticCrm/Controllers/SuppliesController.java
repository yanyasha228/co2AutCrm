package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Models.ModelEnums.SupplyStatus;
import com.co2AutomaticCrm.Models.Supply;
import com.co2AutomaticCrm.Services.SupplyProviderService;
import com.co2AutomaticCrm.Services.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Component
@PreAuthorize("hasAuthority('STOREKEEPER')")
@RequestMapping("/supplies")
public class SuppliesController {

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private SupplyProviderService supplyProviderService;

    @GetMapping
    public String suppliesList(Model model,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 50) Pageable pageable,
                               @RequestParam Optional<SupplyStatus> supplyStatus,
                               @RequestParam Optional<Integer> supplyProviderId) {


        Page<Supply> supplyPage = supplyService.findSuppliesWithPagination(supplyProviderService
                        .findById(supplyProviderId.orElse(0))
                        .orElse(null),
                supplyStatus.orElse(null),
                pageable);


        model.addAttribute("supplyStatus", supplyStatus.orElse(null));

        model.addAttribute("supplyProviderId", supplyProviderId.orElse(0));

        model.addAttribute("suppliesPage",
                supplyPage);

        model.addAttribute("supplyProviders", supplyProviderService.findAll());

        return "supplies";

    }

}
