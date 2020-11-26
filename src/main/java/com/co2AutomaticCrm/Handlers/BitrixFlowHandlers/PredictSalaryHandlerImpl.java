package com.co2AutomaticCrm.Handlers.BitrixFlowHandlers;

import com.co2AutomaticCrm.Handlers.HandlersUtils.BitrixDealSalaryCounter;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PredictSalaryHandlerImpl implements PredictSalaryHandler {

    @Autowired
    private BitrixDealSalaryCounter bitrixDealSalaryCounter;

    @Override
    public void handle(BitrixDeal bitrixDeal) throws EntityInconsistencyException {

        bitrixDeal.setExpectedDealSalaryBonus(bitrixDealSalaryCounter.count(bitrixDeal));


    }
}
