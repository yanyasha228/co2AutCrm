package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.RestBitrixDtoMappers;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRowRestBitrixDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProductRowBitrixDtoToBitrixProductRowMapper implements Mapper<BitrixProductRow, ProductRowRestBitrixDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BitrixProductRow toEntity(ProductRowRestBitrixDto dto) {
        if (Objects.isNull(dto)) return null;
        BitrixProductRow bitrixProductRow = modelMapper.map(dto, BitrixProductRow.class);
        bitrixProductRow.setSumPrice(bitrixProductRow.getPrice() * bitrixProductRow.getQuantity());
        bitrixProductRow.setSumDiscount(bitrixProductRow.getDiscount() * bitrixProductRow.getQuantity());
        return bitrixProductRow;
    }

    @Override
    public ProductRowRestBitrixDto toDto(BitrixProductRow entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, ProductRowRestBitrixDto.class);
    }
}
