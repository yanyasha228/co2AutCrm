package com.co2AutomaticCrm.Handlers.HandlersUtils;


import com.co2AutomaticCrm.Models.ManagerDailySalary;
import com.co2AutomaticCrm.Models.ManagerSalaryIncrease;
import com.co2AutomaticCrm.Models.SalaryIncrease;
import com.co2AutomaticCrm.Models.Worker;
import com.co2AutomaticCrm.Services.DailySalaryService;
import com.co2AutomaticCrm.Services.SalaryIncreaseService;
import com.co2AutomaticCrm.Services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ManagerDailySalaryCounter implements WorkerDailySalaryCounter {

    @Autowired
    private SalaryIncreaseService<ManagerSalaryIncrease> salaryIncreaseService;

    @Autowired
    private WorkerService workerService;

    @Autowired
    private DailySalaryService<ManagerDailySalary> dailySalaryService;

    private static float MANAGER_SALARY_RATE = 250;

    @Override
    public void count() {

        LocalDate localDate = LocalDate.now();

        HashMap<Worker, List<SalaryIncrease>> managerSIListHashMap = new HashMap<>();


        List<ManagerSalaryIncrease> salaryIncreases = salaryIncreaseService.findAllByCreationDate(localDate)
                .stream()
                .filter(managerSalaryIncrease -> managerSalaryIncrease.getDailySalary() == null)
                .collect(Collectors.toList());


        for (ManagerSalaryIncrease salInc :
                salaryIncreases) {

            if (managerSIListHashMap.containsKey(salInc.getWorker())) {
                managerSIListHashMap.get(salInc.getWorker()).add(salInc);

            } else {

                managerSIListHashMap.put(salInc.getWorker(), new ArrayList<>());
                managerSIListHashMap.get(salInc.getWorker()).add(salInc);

            }

        }

        for (Map.Entry<Worker, List<SalaryIncrease>> entry : managerSIListHashMap.entrySet()) {

            ManagerDailySalary managerDailySalary = new ManagerDailySalary();

            managerDailySalary.setWorker(entry.getKey());

            managerDailySalary.setSalaryIncreases(entry.getValue());

            managerDailySalary.setSumDailySalaryAmount(MANAGER_SALARY_RATE + countManagerDailySumSalaryValue(entry.getValue()));

            dailySalaryService.save(managerDailySalary);

        }

    }

    @Override
    public void countByWorker(Long workerId) {

        Optional<Worker> worker = workerService.findById(workerId);

        if (worker.isPresent()) {

            LocalDate localDate = LocalDate.now();

            List<SalaryIncrease> salaryIncreases = salaryIncreaseService.findAllByCreationDate(localDate)
                    .stream()
                    .filter(managerSalaryIncrease -> managerSalaryIncrease.getDailySalary() == null && managerSalaryIncrease.getWorker().getId().longValue() == workerId)
                    .collect(Collectors.toList());

            ManagerDailySalary managerDailySalary = new ManagerDailySalary();

            managerDailySalary.setWorker(worker.get());

            managerDailySalary.setSalaryIncreases(salaryIncreases);

            managerDailySalary.setSumDailySalaryAmount(MANAGER_SALARY_RATE + countManagerDailySumSalaryValue(salaryIncreases));

            dailySalaryService.save(managerDailySalary);

        }


    }

    private float countManagerDailySumSalaryValue(List<SalaryIncrease> salaryIncreases) {

        float sumDSal = 0;

        for (SalaryIncrease salatyIncreseIn :
                salaryIncreases) {
            sumDSal += salatyIncreseIn.getSalaryIncreaseAmount();
        }

        return sumDSal;
    }

}
