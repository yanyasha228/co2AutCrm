package com.co2AutomaticCrm.Handlers.BitrixFlowHandlers;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.InsufficientProductAmountException;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.ReceiptUtils.DealReceiptService;
import com.co2AutomaticCrm.RestServices.BitrixRestServices.DealRestBitrixService;
import com.co2AutomaticCrm.RestServices.BitrixRestServices.NotifyRestBitrixService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.PrintException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class BitrixDealFlowHandlerImpl implements BitrixDealFlowHandler {

    private final static Map<String, String> dealStatusMap = new HashMap<String, String>() {
        {
            put("Уточнение", "NEW");
            put("Обработан", "EXECUTING");
            put("Подтвержден", "PREPARATION");
            put("Удален", "1");
            put("Сборка", "PREPAYMENT_INVOICE");
            put("Отправлен", "FINAL_INVOICE");
            put("Успешная сделка", "WON");
            put("Сделка провалена", "LOSE");
        }
    };

    public final static String NEW_STAGE = "NEW";

    public final static String PROCESSED_STAGE = "EXECUTING";

    public final static String CONFIRMED_STAGE = "PREPARATION";

    public final static String DELETE_STAGE = "1";

    public final static String ASSEMBLING_STAGE = "PREPAYMENT_INVOICE";

    public final static String SENT_STAGE = "FINAL_INVOICE";

    public final static String WON_STAGE = "WON";

    public final static String LOSE_STAGE = "LOSE";


    @Autowired
    private DealRestBitrixService dealRestBitrixService;

    @Autowired
    private NotifyRestBitrixService notifyRestBitrixService;

    @Autowired
    private ManagerSalaryIncreaseHandler managerSalaryIncreaseHandler;

    @Autowired
    private ManagerSalaryDecreaseHandler managerSalaryDecreaseHandler;

    @Autowired
    private PredictSalaryHandler predictSalaryHandler;

    @Autowired
    private DealReceiptService dealReceiptService;

    @Autowired
    private ProductRowsWholeSaleHandler productRowsWholeSaleHandler;

    @Autowired
    private StoreKeepHandler storeKeepHandler;


    @Override
    public void handle(Integer bitrixDealId) {

        Optional<BitrixDeal> bitrixDealOpt = dealRestBitrixService.get(bitrixDealId);

        if (bitrixDealOpt.isPresent()) {
            BitrixDeal bitrixDeal = bitrixDealOpt.get();
            man(bitrixDeal);

        }


    }

    private void man(BitrixDeal bitrixDeal) {

        System.out.println("Bitrix deal id: " + bitrixDeal.getId());

        System.out.println("Bitrix deal stage: " + bitrixDeal.getStage());

//            String previousDealStage = bitrixDeal.getPreviousStage();

        switch (bitrixDeal.getStage()) {

            case NEW_STAGE:
                bitrixDeal.setPreviousStage(NEW_STAGE);
//                    notifyRestBitrixService.sentNotify(bitrixDeal.getManager(), "hui");
                break;

            case PROCESSED_STAGE:
                System.out.println("Start counting");
                try {
                    productRowsWholeSaleHandler.handle(bitrixDeal);
                    bitrixDeal.setPreviousStage(PROCESSED_STAGE);
                } catch (EntityInconsistencyException e) {
                    e.printStackTrace();
                    notifyRestBitrixService.sentNotify(bitrixDeal.getManager(), e.getMessage() + " Сделка: " + bitrixDeal.getId());
                    bitrixDeal.setStage(NEW_STAGE);
                }
                System.out.println("Fin counting");
                break;

            case CONFIRMED_STAGE:

                try {
                    storeKeepHandler.handle(bitrixDeal);
                    productRowsWholeSaleHandler.handle(bitrixDeal);
                    bitrixDeal.setPreviousStage(CONFIRMED_STAGE);
                } catch (InsufficientProductAmountException | EntityInconsistencyException | ImpossibleRestUpdateEntityException e) {
                    e.printStackTrace();
                    notifyRestBitrixService.sentNotify(bitrixDeal.getManager(), e.getMessage() + " Сделка: " + bitrixDeal.getId());
                    bitrixDeal.setStage(PROCESSED_STAGE);
                }

                break;

            case DELETE_STAGE:
                try {
                    storeKeepHandler.handle(bitrixDeal);
                    bitrixDeal.setPreviousStage(DELETE_STAGE);
                } catch (InsufficientProductAmountException | EntityInconsistencyException | ImpossibleRestUpdateEntityException e) {
                    e.printStackTrace();
                    notifyRestBitrixService.sentNotify(bitrixDeal.getManager(), e.getMessage() + " Сделка: " + bitrixDeal.getId());
                    bitrixDeal.setStage(bitrixDeal.getPreviousStage());
                }
                break;

            case ASSEMBLING_STAGE:
                try {
                    storeKeepHandler.handle(bitrixDeal);
                    productRowsWholeSaleHandler.handle(bitrixDeal);

                    if (!bitrixDeal.isPrintReceiptFlag()) {
                        System.out.println("Start Printing");
                        dealReceiptService.print(bitrixDeal);
                        bitrixDeal.setPrintReceiptFlag(true);
                        System.out.println("Fin Printing");
                    }
//                    predictSalaryHandler.handle(bitrixDeal);
                    bitrixDeal.setPreviousStage(ASSEMBLING_STAGE);
                } catch (InsufficientProductAmountException | EntityInconsistencyException | PrintException | DocumentException | ImpossibleRestUpdateEntityException | IOException e) {
                    e.printStackTrace();
                    notifyRestBitrixService.sentNotify(bitrixDeal.getManager(), e.getMessage() + " Сделка: " + bitrixDeal.getId());
                    bitrixDeal.setStage(bitrixDeal.getPreviousStage());
                }

                break;

            case SENT_STAGE:
                System.out.println("Start pred salary counting");
                try {
                    storeKeepHandler.handle(bitrixDeal);
                    productRowsWholeSaleHandler.handle(bitrixDeal);
//                    if (!bitrixDeal.isPickupFlag()) managerSalaryIncreaseHandler.handle(bitrixDeal);
                    bitrixDeal.setPreviousStage(SENT_STAGE);
                } catch (InsufficientProductAmountException | EntityInconsistencyException | ImpossibleRestUpdateEntityException e) {
                    e.printStackTrace();
                    notifyRestBitrixService.sentNotify(bitrixDeal.getManager(), e.getMessage() + " Сделка: " + bitrixDeal.getId());
                    bitrixDeal.setStage(bitrixDeal.getPreviousStage());
                }


                break;

            case WON_STAGE:

                if (bitrixDeal.getPreviousStage().equalsIgnoreCase(SENT_STAGE) || bitrixDeal.isPickupFlag()) {

                    try {
//                        managerSalaryIncreaseHandler.handle(bitrixDeal);
                        bitrixDeal.setPreviousStage(WON_STAGE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        notifyRestBitrixService.sentNotify(bitrixDeal.getManager(), e.getMessage() + " Сделка: " + bitrixDeal.getId());
                        bitrixDeal.setStage(bitrixDeal.getPreviousStage());
                    }
                } else {
                    bitrixDeal.setStage(bitrixDeal.getPreviousStage());
                    notifyRestBitrixService.sentNotify(bitrixDeal.getManager(), "Не установлен флаг САМОВЫВОЗ сделка " + bitrixDeal.getId() + " возвращена");

                }

                break;

            case LOSE_STAGE:
                try {
//                    managerSalaryDecreaseHandler.handle(bitrixDeal);
                    bitrixDeal.setPreviousStage(LOSE_STAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    notifyRestBitrixService.sentNotify(bitrixDeal.getManager(), e.getMessage() + " Сделка: " + bitrixDeal.getId());
                    bitrixDeal.setStage(bitrixDeal.getPreviousStage());

                }


                break;


        }

        try {
            dealRestBitrixService.update(bitrixDeal);
        } catch (ImpossibleRestUpdateEntityException e) {
            e.printStackTrace();
            notifyRestBitrixService.sentNotify(bitrixDeal.getManager(), e.getMessage() + " Сделка: " + bitrixDeal.getId());

        }

    }


}
