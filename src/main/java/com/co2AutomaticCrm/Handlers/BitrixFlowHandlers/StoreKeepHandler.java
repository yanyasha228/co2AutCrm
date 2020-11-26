package com.co2AutomaticCrm.Handlers.BitrixFlowHandlers;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.InsufficientProductAmountException;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;

public interface StoreKeepHandler {

    void handle(BitrixDeal bitrixDeal) throws InsufficientProductAmountException, EntityInconsistencyException, ImpossibleRestUpdateEntityException;

}
