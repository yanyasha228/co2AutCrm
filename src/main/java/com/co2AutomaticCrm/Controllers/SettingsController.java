package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Services.AppSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("settings")
public class SettingsController {


    @Autowired
    private AppSettingsService appSettingsService;

    @RequestMapping
    public String adminSettings(Model model) {

        model.addAttribute("appSettingsModel", appSettingsService.getSettings());
        return "settings";
    }


}