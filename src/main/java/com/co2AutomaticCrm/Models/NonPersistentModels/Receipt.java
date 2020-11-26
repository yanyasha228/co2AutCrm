package com.co2AutomaticCrm.Models.NonPersistentModels;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Receipt {

    private String dealId;

    private String dealDate;

    private String dealManagerDescription;

    private String dealComment;

    private String dealContactDescription;

    private String dealContactNumber;

    private String dealPaymentOption;

    private String dealSumPrice;

    private String dealSumDiscount;

    private String dealDeliveryPlace;

    private List<BitrixProductRow> bitrixProductRows;

    private boolean discountExist;

    private boolean rozetkaFlag;

    private boolean pickupFlag;


}
