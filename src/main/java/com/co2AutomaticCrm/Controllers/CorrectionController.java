package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Services.CorrectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/corrections/{id}")
public class CorrectionController {

    @Autowired
    private CorrectionService correctionService;

    @GetMapping("show")
    public String showCorrection(Model model,
                                 @PathVariable Long id) {

        model.addAttribute("correction", correctionService.findById(id).orElse(null));

        return "correctionShow";
    }

}
