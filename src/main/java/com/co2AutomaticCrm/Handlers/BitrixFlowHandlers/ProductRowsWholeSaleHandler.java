package com.co2AutomaticCrm.Handlers.BitrixFlowHandlers;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;

public interface ProductRowsWholeSaleHandler {

    void handle(BitrixDeal bitrixDeal) throws EntityInconsistencyException;
}
