package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.RestBitrixDtoMappers;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixContact;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ContactRestBitrixDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ContactRestBitrixDtoToBitrixContactMapper implements Mapper<BitrixContact, ContactRestBitrixDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BitrixContact toEntity(ContactRestBitrixDto contactRestBitrixDto) {

        if (Objects.isNull(contactRestBitrixDto)) return null;

        BitrixContact bitrixContact = modelMapper.map(contactRestBitrixDto, BitrixContact.class);

        switch (contactRestBitrixDto.getIsWholesaleFlag()) {
            case "":
                bitrixContact.setWholesaleFlag(false);
                break;
            case "0":
                bitrixContact.setWholesaleFlag(false);
                break;
            case "1":
                bitrixContact.setWholesaleFlag(true);
                break;

            case "false":
                bitrixContact.setWholesaleFlag(false);
                break;

        }

        return bitrixContact;

    }

    @Override
    public ContactRestBitrixDto toDto(BitrixContact entity) {
        if (Objects.isNull(entity)) return null;

        ContactRestBitrixDto contactRestBitrixDto = modelMapper.map(entity, ContactRestBitrixDto.class);

        if (entity.isWholesaleFlag()) contactRestBitrixDto.setIsWholesaleFlag("1");
        else contactRestBitrixDto.setIsWholesaleFlag("0");

        return contactRestBitrixDto;

    }


}
