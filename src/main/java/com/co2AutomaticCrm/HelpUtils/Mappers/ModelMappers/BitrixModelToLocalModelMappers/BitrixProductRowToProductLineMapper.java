package com.co2AutomaticCrm.HelpUtils.Mappers.ModelMappers.BitrixModelToLocalModelMappers;

import com.co2AutomaticCrm.HelpUtils.CustomExceptions.EntityInconsistencyException;
import com.co2AutomaticCrm.Models.BitrixModels.BitrixProductRow;
import com.co2AutomaticCrm.Models.ProductLine;

public interface BitrixProductRowToProductLineMapper {

    ProductLine toProductLine(BitrixProductRow productRow) throws EntityInconsistencyException;

    BitrixProductRow toBitrixProductRow(ProductLine productLine);

}
