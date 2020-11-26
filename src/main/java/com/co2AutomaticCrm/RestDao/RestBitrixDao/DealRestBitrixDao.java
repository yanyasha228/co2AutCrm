package com.co2AutomaticCrm.RestDao.RestBitrixDao;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.ImpossibleRestUpdateEntityException;
import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.DealRestBitrixDto;

import java.util.List;
import java.util.Optional;

public interface DealRestBitrixDao {

    Optional<DealRestBitrixDto> get(Integer id);

    Optional<DealRestBitrixDto> update(DealRestBitrixDto dealRestBitrixDto) throws ImpossibleRestUpdateEntityException;

    List<DealRestBitrixDto> getByStageId(String stageId);
}
