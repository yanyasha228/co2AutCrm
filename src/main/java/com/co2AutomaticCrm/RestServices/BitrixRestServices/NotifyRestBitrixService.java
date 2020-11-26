package com.co2AutomaticCrm.RestServices.BitrixRestServices;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixUser;

public interface NotifyRestBitrixService {

    boolean sentNotify(BitrixUser bitrixUser, String message);
}
