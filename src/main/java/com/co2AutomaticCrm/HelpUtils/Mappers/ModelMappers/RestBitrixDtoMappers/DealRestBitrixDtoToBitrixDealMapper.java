package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.RestBitrixDtoMappers;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.DealRestBitrixDto;
import com.co2AutomaticCrm.Models.NovaPochtaModels.NovaPochtaTrackingDocument;
import com.co2AutomaticCrm.RestServices.BitrixRestServices.ContactRestBitrixService;
import com.co2AutomaticCrm.RestServices.BitrixRestServices.ProductRowRestBitrixService;
import com.co2AutomaticCrm.RestServices.BitrixRestServices.UserRestBitrixService;
import com.co2AutomaticCrm.RestServices.NovaPochtaRestServices.TrackingNovaPochtaRestService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class DealRestBitrixDtoToBitrixDealMapper implements Mapper<BitrixDeal, DealRestBitrixDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ContactRestBitrixService contactRestBitrixService;

    @Autowired
    private UserRestBitrixService userRestBitrixService;

    @Autowired
    private ProductRowRestBitrixService productRowRestBitrixService;

    @Autowired
    private TrackingNovaPochtaRestService trackingNovaPochtaRestService;

    @Override
    public BitrixDeal toEntity(DealRestBitrixDto dto) {
        if (Objects.isNull(dto)) return null;

        BitrixDeal bitrixDeal = modelMapper.map(dto, BitrixDeal.class);

        bitrixDeal.setPickupFlag(mapStringBitrixFlagToBooleanFlag(dto.getPickupFlag()));
        bitrixDeal.setRozetkaFlag(mapStringBitrixFlagToBooleanFlag(dto.getRozetkaFlag()));
        bitrixDeal.setStoreProcCalcFlag(mapStringBitrixFlagToBooleanFlag(dto.getStoreProcCalcFlag()));
        bitrixDeal.setWholeSalePriceCalcFlag(mapStringBitrixFlagToBooleanFlag(dto.getWholeSalePriceCalcFlag()));
        bitrixDeal.setPrintReceiptFlag(mapStringBitrixFlagToBooleanFlag(dto.getPrintReceiptFlag()));


        bitrixDeal.setManager(userRestBitrixService.get(dto.getUserId()).orElse(null));
        bitrixDeal.setProductRows(productRowRestBitrixService.getByDealId(dto.getId()));
        bitrixDeal.setContact(contactRestBitrixService.get(dto.getContactId()).orElse(null));

        if (!Objects.isNull(bitrixDeal.getContact())) {
            if (!bitrixDeal.getContact().getPhoneNumber().isEmpty() && !bitrixDeal.getDocumentNumber().isEmpty()) {
                bitrixDeal.setAddress(trackingNovaPochtaRestService
                        .getAddress(new NovaPochtaTrackingDocument(bitrixDeal.getDocumentNumber(), bitrixDeal.getContact().getPhoneNumber())).orElse(null));
            }
        }

        return bitrixDeal;
    }

    @Override
    public DealRestBitrixDto toDto(BitrixDeal entity) {
        if (Objects.isNull(entity)) return null;
        DealRestBitrixDto dealRestBitrixDto = modelMapper.map(entity, DealRestBitrixDto.class);

        if (entity.isPickupFlag()) dealRestBitrixDto.setPickupFlag("1");
        else dealRestBitrixDto.setPickupFlag("0");

        if (entity.isStoreProcCalcFlag()) dealRestBitrixDto.setStoreProcCalcFlag("1");
        else dealRestBitrixDto.setStoreProcCalcFlag("0");

        if (entity.isWholeSalePriceCalcFlag()) dealRestBitrixDto.setWholeSalePriceCalcFlag("1");
        else dealRestBitrixDto.setWholeSalePriceCalcFlag("0");

        if (entity.isPrintReceiptFlag()) dealRestBitrixDto.setPrintReceiptFlag("1");
        else dealRestBitrixDto.setPrintReceiptFlag("0");

        if (entity.isRozetkaFlag()) dealRestBitrixDto.setRozetkaFlag("1");
        else dealRestBitrixDto.setRozetkaFlag("0");

        dealRestBitrixDto.setContactId(entity.getContact().getId());
        dealRestBitrixDto.setUserId(entity.getManager().getId());

        dealRestBitrixDto.setExpectedDealSalaryBonus(String.valueOf(entity.getExpectedDealSalaryBonus()));
        dealRestBitrixDto.setActualDealSalaryBonus(String.valueOf(entity.getActualDealSalaryBonus()));

        return dealRestBitrixDto;
    }

    private boolean mapStringBitrixFlagToBooleanFlag(String stringBitrixFlag) {

        switch (stringBitrixFlag) {
            case "":
                return false;
            case "0":
                return false;
            case "1":
                return true;
            case "false":
                return false;
        }

        return false;
    }


}
