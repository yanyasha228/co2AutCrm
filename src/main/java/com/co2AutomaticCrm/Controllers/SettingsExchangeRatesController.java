package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.Models.AppSettings;
import com.co2AutomaticCrm.Services.AppSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("settings/exchangeRates")
public class SettingsExchangeRatesController {

//    private static final Logger logger = Logger.getLogger(SettingsExchangeRatesController.class);

    @Autowired
    private AppSettingsService appSettingsService;

    @RequestMapping
    public String adminSettingsExchangeRates(Model model) {
        model.addAttribute("appSettingsModel", appSettingsService.getSettings());
        return "settingsExchangeRates";
    }

    @PostMapping("submit")
    public String updateSettings(Model model, @ModelAttribute AppSettings appSettings) throws ImpossibleEntitySaveUpdateException {

        if (appSettings != null)
            appSettingsService.update(appSettings);

        return "redirect:../";
    }


    @ExceptionHandler(ImpossibleEntitySaveUpdateException.class)
    public String handleOrderManipulationException(Model model, Exception ex) {
//        logger.error(ex.toString());

        model.addAttribute("error", "Ошибка : " + ex.getMessage());

        return "error";
    }
}