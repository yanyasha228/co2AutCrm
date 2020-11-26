package com.co2AutomaticCrm.RestServices.BitrixRestServices;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixNotify;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixUser;
import com.co2AutomaticCrm.RestDao.RestBitrixDao.NotifyRestBitrixDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotifyRestBitrixServiceImpl implements NotifyRestBitrixService {

    @Autowired
    private NotifyRestBitrixDao notifyRestBitrixDao;

    @Override
    public boolean sentNotify(BitrixUser bitrixUser, String message) {

        return notifyRestBitrixDao.post(new BitrixNotify(bitrixUser, message));

    }
}
