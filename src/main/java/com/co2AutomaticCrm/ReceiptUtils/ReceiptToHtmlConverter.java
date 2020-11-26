package com.co2AutomaticCrm.ReceiptUtils;

import com.co2AutomaticCrm.Models.NonPersistentModels.Receipt;

public interface ReceiptToHtmlConverter {

    String convert(Receipt receipt);

}
