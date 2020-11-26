package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetProductListBitrixWithPaginationRequest implements BitrixRestRequest {

    public GetProductListBitrixWithPaginationRequest(int start) {
        this.start = start;
    }

    private int start;

    private String[] select = {"ID", "PROPERTY_104"};


}
