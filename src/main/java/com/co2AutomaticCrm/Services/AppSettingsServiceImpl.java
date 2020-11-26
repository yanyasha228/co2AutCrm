package com.co2AutomaticCrm.Services;

import com.co2AutomaticCrm.Dao.AppSettingsDao;
import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleEntitySaveUpdateException;
import com.co2AutomaticCrm.Models.AppSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppSettingsServiceImpl implements AppSettingsService {

    @Autowired
    private AppSettingsDao appSettingsDao;

    @Override
    public AppSettings update(AppSettings appSettings) throws ImpossibleEntitySaveUpdateException {

        if (appSettings == null || appSettings.getId() != 1)
            throw new ImpossibleEntitySaveUpdateException("Settings Model is NULL or ID is not corresponds to original settings id");

        return appSettingsDao.save(appSettings);
    }

    @Override
    public Optional<AppSettings> getSettings() {
        return appSettingsDao.findById(1);
    }

    @Override
    public boolean exist() {
        return appSettingsDao.existsById(1);
    }
}
