package com.co2AutomaticCrm.RestControllers;

import com.co2AutomaticCrm.Models.ManagerSalaryDecrease;
import com.co2AutomaticCrm.Models.SalaryDecrease;
import com.co2AutomaticCrm.Models.Worker;
import com.co2AutomaticCrm.Services.SalaryDecreaseService;
import com.co2AutomaticCrm.Services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/rest/salary/manager/decreases")
public class SalaryDecreasesRestController {

    @Autowired
    private SalaryDecreaseService salaryDecreaseService;

    @Autowired
    private WorkerService workerService;

    @PostMapping("add")
    public void addSalaryDecrease(@RequestParam Optional<Float> amount, @AuthenticationPrincipal Worker worker) {

        if (amount.isPresent()) {
            SalaryDecrease salaryDecrease = new ManagerSalaryDecrease();
            salaryDecrease.setWorker(worker);
            salaryDecrease.setSalaryDecreaseAmount(amount.get());

            salaryDecreaseService.save(salaryDecrease);

        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("nullify")
    public void nullifySalaryDecreaseBalance(@RequestParam Optional<Long> managerId) {

        if (managerId.isPresent()) {

            Optional<Worker> workerOpt = workerService.findById(managerId.get());

            if (workerOpt.isPresent()) {
                Worker manager = workerOpt.get();
                manager.nullifySalaryDecreaseBalance();
                workerService.save(manager);
            }
        }

    }

}
