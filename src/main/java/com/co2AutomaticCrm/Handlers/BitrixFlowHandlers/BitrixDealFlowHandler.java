package com.co2AutomaticCrm.Handlers.BitrixFlowHandlers;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.itextpdf.text.DocumentException;

import javax.print.PrintException;
import java.io.IOException;

public interface BitrixDealFlowHandler {

    void handle(Integer bitrixDealId) throws DocumentException,
            PrintException,
            IOException,
            ImpossibleRestUpdateEntityException,
            EntityInconsistencyException;

}
