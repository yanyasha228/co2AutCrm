package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.Models.AppSettings;

import java.util.Optional;

public interface AppSettingsService {

    AppSettings update(AppSettings appSettingsModel) throws ImpossibleEntitySaveUpdateException;


    Optional<AppSettings> getSettings();

    boolean exist();
}
