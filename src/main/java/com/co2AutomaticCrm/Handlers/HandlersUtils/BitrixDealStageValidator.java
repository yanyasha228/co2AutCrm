package com.co2AutomaticCrm.Handlers.HandlersUtils;

import java.util.Optional;

public interface BitrixDealStageValidator {

    boolean isValidNextStage(String stage, String previousStage);


    Optional<String> getNextStage(String stage);

    Optional<String> getPreviousStage(String stage);

}
