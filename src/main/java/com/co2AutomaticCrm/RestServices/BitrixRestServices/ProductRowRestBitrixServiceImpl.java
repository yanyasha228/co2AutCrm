package com.co2AutomaticCrm.RestServices.BitrixRestServices;

import com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.Mapper;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixDeal;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRowRestBitrixDto;
import com.co2AutomaticCrm.RestDao.RestBitrixDao.ProductRowRestBitrixDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductRowRestBitrixServiceImpl implements ProductRowRestBitrixService {

    @Autowired
    private ProductRowRestBitrixDao productRowRestBitrixDao;

    @Autowired
    private Mapper<BitrixProductRow, ProductRowRestBitrixDto> mapper;

    @Override
    public List<BitrixProductRow> getByDealId(Long id) {
        return productRowRestBitrixDao.getByDealId(id).stream()
                .map(productRowRestBitrixDto -> mapper.toEntity(productRowRestBitrixDto))
                .collect(Collectors.toList());
    }

    @Override
    public boolean update(BitrixDeal bitrixDeal) {
        return productRowRestBitrixDao.update(bitrixDeal.getId(), bitrixDeal.getProductRows().stream()
                .map(productRow -> mapper.toDto(productRow)).collect(Collectors.toList()));
    }

}
