package com.co2AutomaticCrm.Models.BitrixModels;

import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaAddress;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BitrixDeal {

    private long id;

    private float sumPrice;

    private String stage;

    private String currency;

    private String comment;

    private String paymentOptionId;

    private String documentNumber;

    private boolean rozetkaFlag;

    private boolean pickupFlag;

    private boolean wholeSalePriceCalcFlag;

//

    private boolean printReceiptFlag;

    //
    private boolean storeProcCalcFlag;

    private String previousStage;

    private BitrixUser manager;

    private List<BitrixProductRow> productRows;

    private BitrixContact contact;

    private NovaPochtaAddress address;

    private float expectedDealSalaryBonus;

    private float actualDealSalaryBonus;

}
