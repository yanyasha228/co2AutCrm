package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Models.RozetkaModels.RozetkaProductParam;
import com.co2AutomaticCrm.Services.RozetkaModelServices.RozetkaProductParamService;
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
@RequestMapping("settings/rozetka/params")
public class RozetkaProductParamsController {

    @Autowired
    private RozetkaProductParamService rozetkaProductParamService;

    @GetMapping
    private String paramsList(Model model, @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC, size = 15) Pageable pageable, @RequestParam Optional<String> searchName) {

        Page<RozetkaProductParam> paramsPage = rozetkaProductParamService.findParamsWithPagination(searchName.orElse(null), pageable);

        model.addAttribute("searchName", searchName.orElse(""));

        model.addAttribute("paramsPage", paramsPage);

        return "rozetkaParams";

    }
}
