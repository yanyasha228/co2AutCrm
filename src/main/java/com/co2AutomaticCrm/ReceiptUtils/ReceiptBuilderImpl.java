package com.co2AutomaticCrm.ReceiptUtils;

import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;
import com.co2AutomaticCrm.Models.NonPersistentModels.Receipt;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class ReceiptBuilderImpl implements ReceiptBuilder {

    private final static Map<String, String> dealPaymentOptionMap = new HashMap<String, String>() {
        {
            put("44", "Наложка");
            put("46", "Оценка");
            put("48", "Оплата частями");
            put("50", "Расрочка");
            put("52", "Наличные");
        }
    };


    @Override
    public Receipt build(BitrixDeal bitrixDeal) {
        if (Objects.isNull(bitrixDeal)) return null;
        Receipt receipt = new Receipt();

        receipt.setDealId(String.valueOf(bitrixDeal.getId()));
        receipt.setDealDate(LocalDate.now().toString());
        receipt.setDealManagerDescription(bitrixDeal.getManager().getName() + "(" +
                bitrixDeal.getManager().getEmail() + ")");

        receipt.setDealComment(bitrixDeal.getComment());

        if (!Objects.isNull(bitrixDeal.getContact())) {
            receipt.setDealContactDescription(getContactNameForReceipt(bitrixDeal));

            receipt.setDealContactNumber(bitrixDeal.getContact().getPhoneNumber());

        } else receipt.setDealContactDescription("");


        if (!Objects.isNull(bitrixDeal.getAddress())) {
            receipt.setDealDeliveryPlace(bitrixDeal.getAddress().getDeliveryPlaceName());
        } else receipt.setDealDeliveryPlace("");

        receipt.setDealPaymentOption(dealPaymentOptionMap.get(bitrixDeal.getPaymentOptionId()));

        receipt.setDealSumPrice(String.valueOf(bitrixDeal.getSumPrice()));

        float sumDiscount = calculateSumDiscount(bitrixDeal);

        if (!(sumDiscount == 0)) {
            receipt.setDiscountExist(true);
            receipt.setDealSumDiscount(String.valueOf(sumDiscount));
        } else receipt.setDiscountExist(false);

        receipt.setBitrixProductRows(bitrixDeal.getProductRows());

        receipt.setRozetkaFlag(bitrixDeal.isRozetkaFlag());

        receipt.setPickupFlag(bitrixDeal.isPickupFlag());


        return receipt;
    }

    private String getContactNameForReceipt(BitrixDeal bitrixDeal) {

        StringBuilder contName = new StringBuilder("");

        if (!Objects.isNull(bitrixDeal.getContact().getLastName())) {
            contName.append(bitrixDeal.getContact().getLastName());
            contName.append(" ");
        }

        contName.append(bitrixDeal.getContact().getName());
        contName.append(" ");

        if (!Objects.isNull(bitrixDeal.getContact().getSecondName())) {
            contName.append(bitrixDeal.getContact().getSecondName());
        }

        return contName.toString();


    }

    private float calculateSumDiscount(BitrixDeal bitrixDeal) {

        float sumDiscount = 0;

        for (BitrixProductRow productRow : bitrixDeal.getProductRows()) {
            sumDiscount += productRow.getSumDiscount();
        }

        return sumDiscount;
    }
}
