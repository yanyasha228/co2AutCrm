package com.co2AutomaticCrm.ReceiptUtils;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.Models.NonPersistentModels.Receipt;

public interface ReceiptBuilder {

    Receipt build(BitrixDeal bitrixDeal);

}