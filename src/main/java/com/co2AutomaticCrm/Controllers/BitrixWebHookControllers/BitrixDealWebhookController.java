package com.co2AutomaticCrm.Controllers.BitrixWebHookControllers;

import com.co2AutomaticCrm.Handlers.BitrixFlowHandlers.BitrixDealFlowHandler;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.PrintException;
import java.io.IOException;

@RestController
@RequestMapping("/bitrix/hook/deal")
public class BitrixDealWebhookController {

    @Autowired
    private BitrixDealFlowHandler bitrixDealFlowHandler;

//    private final static Map<String,String> dealStatusMap = new HashMap<String, String>(){{
//        put("Уточнение" , "NEW");
//        put("Подтвержден" , "PREPARATION");
//        put("Сборка","PREPAYMENT_INVOICE");
//        put("Обработан","EXECUTING");
//        put("Удален","1");
//        put("Отправлен" , "FINAL_INVOICE");
//        put("Успешная сделка", "WON");
//        put("Сделка провалена" , "LOSE");
//    }
//    };

    @PostMapping("{id}")
    public void bitrixHookHandle(@PathVariable Integer id) throws DocumentException, PrintException, IOException, ImpossibleRestUpdateEntityException, EntityInconsistencyException {

        bitrixDealFlowHandler.handle(id);
    }

    @ExceptionHandler({DocumentException.class, PrintException.class, IOException.class})
    public String handleOrderManipulationException(Model model, Exception ex) {
//        logger.error(ex.toString());

        model.addAttribute("error", ex.getClass().getName());

        return "error";
    }

}
