package com.co2AutomaticCrm.Models.HelpRestModels.HelpRestBitrixModels.Requests;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class GetBitrixDealsByStageIdRequest implements BitrixRestRequest {

    public GetBitrixDealsByStageIdRequest(String stage) {

        this.select.add("ID");
        this.select.add("OPPORTUNITY");
        this.select.add("STAGE_ID");
        this.select.add("CURRENCY_ID");
        this.select.add("CONTACT_ID");
        this.select.add("ASSIGNED_BY_ID");
        this.select.add("COMMENTS");
        this.select.add("UF_CRM_1575820479003");
        this.select.add("UF_CRM_TTN_NUM");
        this.select.add("UF_CRM_1575820544163");
        this.select.add("UF_CRM_1575820572770");
        this.select.add("UF_CRM_1575821896038");
        this.select.add("UF_CRM_1575821951932");
        this.select.add("UF_CRM_1575820621129");
        this.select.add("UF_CRM_1575820635355");

        this.filter.put("STAGE_ID", stage);
    }


    private HashMap<String, String> filter = new HashMap<>();

    private ArrayList<String> select = new ArrayList<>();
}
