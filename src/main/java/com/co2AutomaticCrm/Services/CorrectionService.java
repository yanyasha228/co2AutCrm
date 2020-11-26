package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleAmountDecreasingException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.Models.Correction;
import com.co2AutomaticCrm.Models.HelpModels.ProductManipulationLine;
import com.co2AutomaticCrm.Models.ModelEnums.CorrectionType;
import com.co2AutomaticCrm.Models.User;

import java.util.List;
import java.util.Optional;

public interface CorrectionService {

    Correction saveStrictCorrection(User user,
                                    CorrectionType correctionType,
                                    String description,
                                    Long id ,
                                    Integer amount) throws ImpossibleEntitySaveUpdateException, ImpossibleRestUpdateEntityException, ImpossibleAmountDecreasingException;

    List<Correction> saveAllWithRelativeCorrection(User user,
                                                   CorrectionType correctionType,
                                                   String description,
                                                   List<ProductManipulationLine> productManipulationLines) throws ImpossibleEntitySaveUpdateException, ImpossibleRestUpdateEntityException, ImpossibleAmountDecreasingException;


    Optional<Correction> findById(Long id);
}
