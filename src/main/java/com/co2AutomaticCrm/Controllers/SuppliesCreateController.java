package com.co2AutomaticCrm.Controllers;


import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleAmountDecreasingException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.InsufficientProductAmountException;
import com.co2AutomaticCrm.Models.ModelEnums.SupplyStatus;
import com.co2AutomaticCrm.Models.Supply;
import com.co2AutomaticCrm.Models.User;
import com.co2AutomaticCrm.Services.ProductService;
import com.co2AutomaticCrm.Services.SupplyProviderService;
import com.co2AutomaticCrm.Services.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@PreAuthorize("hasAuthority('STOREKEEPER')")
@RequestMapping("/supplies/create")
public class SuppliesCreateController {

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplyProviderService supplyProviderService;


//    private static final Logger logger = Logger.getLogger(OrdersEditController.class);


    @GetMapping
    public String createOrder(Model model, @RequestParam(required = false) Optional<Long> productId) {


        model.addAttribute("transProduct", productService.findById(productId.orElse(0L)));
        model.addAttribute("supply", new Supply());

        model.addAttribute("supplyProviders", supplyProviderService.findAll());

        return "suppliesCreate";


    }

    @PostMapping("submit")
    public String createOrderSubmit(@ModelAttribute Supply supply,
                                    @AuthenticationPrincipal User user,
                                    @RequestParam Integer supplyProviderId,
                                    @RequestParam SupplyStatus supplyStatus,
                                    @RequestParam Long[] productLineId,
                                    @RequestParam Integer[] productLineProductQua,
                                    @RequestParam(required = false) Long[] productLineIdInputDependent,
                                    @RequestParam(required = false) Integer[] productLineProductQuaDependent) throws ImpossibleEntitySaveUpdateException, ImpossibleAmountDecreasingException, ImpossibleRestUpdateEntityException, InsufficientProductAmountException {

        supplyService.save(supply,
                user,
                supplyProviderId,
                supplyStatus,
                productLineId,
                productLineProductQua,
                productLineIdInputDependent,
                productLineProductQuaDependent);


        return "redirect:/supplies";

    }

    @ExceptionHandler(value = {ImpossibleEntitySaveUpdateException.class,
            ImpossibleAmountDecreasingException.class,
            InsufficientProductAmountException.class})
    public String handleOrderManipulationException(Model model, Exception ex) {
//        logger.error(ex.toString());

        model.addAttribute("error", "Ошибка : " + ex.getMessage());

        return "error";
    }
}
