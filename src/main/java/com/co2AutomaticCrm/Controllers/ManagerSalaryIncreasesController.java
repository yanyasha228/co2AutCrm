package com.co2AutomaticCrm.Controllers;


import com.co2AutomaticCrm.Models.ManagerDailySalary;
import com.co2AutomaticCrm.Models.ManagerSalaryIncrease;
import com.co2AutomaticCrm.Models.ModelEnums.Role;
import com.co2AutomaticCrm.Models.Worker;
import com.co2AutomaticCrm.Services.DailySalaryService;
import com.co2AutomaticCrm.Services.SalaryIncreaseService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("salary/manager/increases")
public class ManagerSalaryIncreasesController {

    @Autowired
    private SalaryIncreaseService<ManagerSalaryIncrease> salaryIncreaseService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private DailySalaryService<ManagerDailySalary> dailySalaryService;

//    @GetMapping
////    @PreAuthorize("hasAuthority('ADMIN')")
//    public String salaryIncreasesListByDailySalaryId( Model model ,
//                                                      @AuthenticationPrincipal Manager manager,
//                                                      @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 5) Pageable pageable,
//                                                      @RequestParam Optional<Long> dailySalaryId ){
//
//        Page<SalaryIncrease> salaryIncreases = salaryIncreaseService.findAllByDailySalaryIdWithPagination(dailySalaryId .orElse(0l),pageable);
//
//        Optional<DailySalary> dailySalary = dailySalaryService.findById(dailySalaryId.orElse(0l));
//
//        if(!dailySalary.isPresent()) return "redirect:/salary/daily";
//
//        model.addAttribute("dailySalary" , dailySalary.get());
//        model.addAttribute("salaryIncreasesPage", salaryIncreases);
//
//
//        return "salaryIncreasesShort";
//    }

    @GetMapping
    public String salaryIncreasesList(Model model,
                                      @AuthenticationPrincipal Worker worker,
                                      @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 50) Pageable pageable,
                                      @RequestParam Optional<Long> workerId) {

        List<Worker> workersList = new ArrayList<>();

        Worker currentWorker;

        Page<ManagerSalaryIncrease> salaryIncreases;

        if (worker.getRoles().contains(Role.ADMIN)) {

            workersList = workerService.findAll();

            workersList.remove(worker);

            currentWorker = workerService.findById(workerId.orElse(0L)).orElse(null);
            if (Objects.isNull(currentWorker)) {
                salaryIncreases = salaryIncreaseService.findAllWithPagination(pageable);
            } else {
                salaryIncreases = salaryIncreaseService.findSalaryIncreasesWithPagination(workerService.findById(workerId.orElse(0L)).orElse(null), pageable);
            }
        } else {
            workersList.add(worker);
            currentWorker = worker;
            salaryIncreases = salaryIncreaseService.findSalaryIncreasesWithPagination(worker, pageable);
        }

        model.addAttribute("workersList", workersList);
        model.addAttribute("currentWorker", currentWorker);
        model.addAttribute("salaryIncreasesPage", salaryIncreases);


        return "salaryIncreases";

    }

    @GetMapping("{dailySalaryId}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    private String dailySalaryById(Model model,
                                   @AuthenticationPrincipal Worker worker,
                                   @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC, size = 50) Pageable pageable,
                                   @PathVariable Long dailySalaryId) {

        Optional<ManagerDailySalary> dailySalary;

        if (dailySalaryId != null) {
            if (dailySalaryId == 0) {
                return "redirect:/salary/daily";
            }
            dailySalary = dailySalaryService.findById(dailySalaryId);

            if (!dailySalary.isPresent()) return "redirect:/salary/daily";
        } else {
            return "redirect:/salary/daily";
        }


        if (!worker.getRoles().contains(Role.ADMIN)) {
            if (worker.getId().longValue() != dailySalary.get().getWorker().getId()) return "redirect:/salary/daily";
        }

        Page<ManagerSalaryIncrease> salaryIncreases = salaryIncreaseService.findAllByDailySalaryIdWithPagination(dailySalaryId, pageable);


        model.addAttribute("dailySalary", dailySalary.orElse(null));
        model.addAttribute("salaryIncreasesPage", salaryIncreases);

        return "salaryIncreasesShort";
    }

}
