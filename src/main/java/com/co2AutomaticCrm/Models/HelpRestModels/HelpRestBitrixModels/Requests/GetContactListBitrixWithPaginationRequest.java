package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetContactListBitrixWithPaginationRequest implements BitrixRestRequest {

    public GetContactListBitrixWithPaginationRequest(int start) {
        this.start = start;
    }

    private int start;

    private String[] select = {"NAME", "SECOND_NAME", "LAST_NAME", "PHONE"};

}
