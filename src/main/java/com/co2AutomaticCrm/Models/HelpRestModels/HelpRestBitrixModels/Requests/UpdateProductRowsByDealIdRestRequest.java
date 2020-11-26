package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto.ProductRowRestBitrixDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateProductRowsByDealIdRestRequest implements BitrixRestRequest {

    public UpdateProductRowsByDealIdRestRequest(Long id, List<ProductRowRestBitrixDto> rows) {
        this.id = id;
        this.rows = rows;
    }

    private Long id;

    private List<ProductRowRestBitrixDto> rows;

}
