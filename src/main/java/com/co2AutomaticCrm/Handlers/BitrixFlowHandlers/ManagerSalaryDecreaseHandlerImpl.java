package com.co2AutomaticCrm.Handlers.BitrixFlowHandlers;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.Models.ManagerSalaryDecrease;
import com.co2AutomaticCrm.Models.Worker;
import com.co2AutomaticCrm.Services.SalaryDecreaseService;
import com.co2AutomaticCrm.Services.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

//Manager Rest service politoyTOP
@Component
public class ManagerSalaryDecreaseHandlerImpl implements ManagerSalaryDecreaseHandler {

    @Autowired
    private SalaryDecreaseService<ManagerSalaryDecrease> salaryDecreaseService;

    @Autowired
    private WorkerService workerService;

    @Override
    public void handle(BitrixDeal bitrixDeal) throws EntityInconsistencyException {

        if (!salaryDecreaseService.findByBitrixDealId(bitrixDeal.getId()).isPresent()) {

            Optional<Worker> managerOpt = workerService.findById((long) bitrixDeal.getManager().getId());

            if (!managerOpt.isPresent())
                throw new EntityInconsistencyException("Deal doesnt have a registred manager for salary counting");

            ManagerSalaryDecrease salaryDecrease = new ManagerSalaryDecrease();

            salaryDecrease.setBitrixDealId(bitrixDeal.getId());

            salaryDecrease.setSalaryDecreaseAmount(bitrixDeal.getExpectedDealSalaryBonus());

            bitrixDeal.setActualDealSalaryBonus(0);

            salaryDecrease.setWorker(managerOpt.get());

            salaryDecreaseService.save(salaryDecrease);

        }


    }
}
