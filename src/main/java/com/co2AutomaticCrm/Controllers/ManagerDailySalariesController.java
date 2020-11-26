package com.co2AutomaticCrm.Controllers;

import com.co2AutomaticCrm.Models.ManagerDailySalary;
import com.co2AutomaticCrm.Models.ModelEnums.Role;
import com.co2AutomaticCrm.Models.Worker;
import com.co2AutomaticCrm.Services.DailySalaryService;
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
@RequestMapping("salary/manager/daily")
public class ManagerDailySalariesController {

    @Autowired
    private DailySalaryService<ManagerDailySalary> dailySalaryService;

    @Autowired
    private WorkerService workerService;

    @GetMapping
    public String dailySalariesList(Model model,
                                    @AuthenticationPrincipal Worker worker,
                                    @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 50) Pageable pageable,
                                    @RequestParam Optional<Long> managerId) {

        List<Worker> workersList = new ArrayList<>();

        Worker currentWorker;

        Page<ManagerDailySalary> dailySalaries;

        if (worker.getRoles().contains(Role.ADMIN)) {

            workersList = workerService.findAll();

            workersList.remove(worker);

            currentWorker = workerService.findById(managerId.orElse(0L)).orElse(null);

            if (Objects.isNull(currentWorker)) {
                dailySalaries = dailySalaryService.findAllWithPagination(pageable);
            } else {
                dailySalaries = dailySalaryService.findDailySalariesWithPagination(null, workerService.findById(managerId.orElse(0L)).orElse(null), pageable);
            }

        } else {

            workersList.add(worker);
            currentWorker = worker;
            dailySalaries = dailySalaryService.findDailySalariesWithPagination(null, worker, pageable);

        }

        model.addAttribute("workersList", workersList);
        model.addAttribute("currentWorker", currentWorker);
        model.addAttribute("dailySalariesPage", dailySalaries);

        return "dailySalaries";

    }


}
