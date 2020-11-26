package com.co2AutomaticCrm.RestControllers;

import com.co2AutomaticCrm.Models.RozetkaModels.RozetkaProductParam;
import com.co2AutomaticCrm.Services.RozetkaModelServices.RozetkaProductParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/rest/settings/rozetka/params")
public class RozetkaProductParamRestController {

    @Autowired
    private RozetkaProductParamService rozetkaProductParamService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add")
    public void addParam(@RequestParam String name) {

        if (!Objects.isNull(name) && !name.isEmpty()) {
            if (!rozetkaProductParamService.findByName(name).isPresent()) {
                RozetkaProductParam rozetkaProductParamNew = new RozetkaProductParam();
                rozetkaProductParamNew.setName(name.trim());
                rozetkaProductParamService.save(rozetkaProductParamNew);
            }
        }

    }

    @PostMapping("delete")
    public void deleteParam(@RequestParam Long id) {
        rozetkaProductParamService.delete(id);
    }

}
