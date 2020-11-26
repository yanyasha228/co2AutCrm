package com.co2AutomaticCrm.Controllers;


import com.co2AutomaticCrm.Models.ManagerSalaryDecrease;
import com.co2AutomaticCrm.Models.ModelEnums.Role;
import com.co2AutomaticCrm.Models.Worker;
import com.co2AutomaticCrm.Services.SalaryDecreaseService;
import com.co2AutomaticCrm.Services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("salary/manager/decreases")
public class ManagerSalaryDecreasesController {

    @Autowired
    private SalaryDecreaseService<ManagerSalaryDecrease> salaryDecreaseService;

    @Autowired
    private WorkerService workerService;

    @GetMapping
    public String salaryDecreasesList(Model model,
                                      @AuthenticationPrincipal Worker worker,
                                      @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 50) Pageable pageable,
                                      @RequestParam Optional<Long> workerId) {

        List<Worker> workersList = new ArrayList<>();

        Worker workerManager;

        Page<ManagerSalaryDecrease> salaryDecreases;

        if (worker.getRoles().contains(Role.ADMIN)) {

            workersList = workerService.findAll();

            workersList.remove(worker);

            workerManager = workerService.findById(workerId.orElse(0L)).orElse(null);
            if (Objects.isNull(workerManager)) {
                salaryDecreases = salaryDecreaseService.findAllWithPagination(pageable);
            } else {
                salaryDecreases = salaryDecreaseService.findSalaryDecreasesWithPagination(workerService.findById(workerId.orElse(0L)).orElse(null), pageable);
            }
        } else {
            workersList.add(worker);
            workerManager = worker;
            salaryDecreases = salaryDecreaseService.findSalaryDecreasesWithPagination(worker, pageable);
        }

        model.addAttribute("workersList", workersList);
        model.addAttribute("currentWorker", workerManager);
        model.addAttribute("salaryDecreasesPage", salaryDecreases);


        return "salaryDecreases";

    }

}