package com.co2AutomaticCrm.RestServices.BitrixRestServices;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;

import java.util.List;

public interface ProductRowRestBitrixService {

    List<BitrixProductRow> getByDealId(Long id);

    boolean update(BitrixDeal bitrixDeal);


}
