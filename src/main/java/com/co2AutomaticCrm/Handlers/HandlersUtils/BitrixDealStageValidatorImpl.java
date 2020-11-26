package com.co2AutomaticCrm.Handlers.HandlersUtils;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Optional;

@Component
public class BitrixDealStageValidatorImpl implements BitrixDealStageValidator {


    private final static LinkedList<String> stageList = new LinkedList<String>();

    public BitrixDealStageValidatorImpl() {
        stageList.add("NEW");
        stageList.add("EXECUTING");
        stageList.add("PREPARATION");
        stageList.add("PREPAYMENT_INVOICE");
        stageList.add("FINAL_INVOICE");
    }

    @Override
    public boolean isValidNextStage(String stage, String previousStage) {

        ListIterator<String> listIterator = stageList.listIterator(stageList.indexOf(stage));

        if (listIterator.hasPrevious()) return listIterator.previous().equals(previousStage);

        return stage.equals("NEW");

    }


    @Override
    public Optional<String> getNextStage(String stage) {
        ListIterator<String> listIterator = stageList.listIterator(stageList.indexOf(stage));
        if (!listIterator.hasNext()) return Optional.empty();
        return Optional.of(listIterator.next());
    }

    @Override
    public Optional<String> getPreviousStage(String stage) {
        ListIterator<String> listIterator = stageList.listIterator(stageList.indexOf(stage));
        if (!listIterator.hasPrevious()) return Optional.empty();
        return Optional.of(listIterator.previous());
    }
}
