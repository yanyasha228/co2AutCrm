package com.co2AutomaticCrm.FilesUtils;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.*;
import com.co2AutomaticCrm.Models.Correction;
import com.co2AutomaticCrm.Models.User;

import java.io.FileNotFoundException;
import java.util.List;

public interface ProductStoreCsvCorrectionsUploader {

    List<Correction> doCorrections(String fileName, User user) throws FileNotFoundException,
            ImpossibleEntitySaveUpdateException,
            ImpossibleAmountDecreasingException,
            ImpossibleRestUpdateEntityException,
            IncorrectFileException, CsvCorrectionResultException;

}
