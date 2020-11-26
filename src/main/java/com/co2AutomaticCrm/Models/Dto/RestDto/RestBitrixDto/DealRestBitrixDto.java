package com.co2AutomaticCrm.Models.Dto.RestDto.RestBitrixDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


///Need to change mapper method toDto before setting access level READ in @JsonProperty(access)

@Data
@NoArgsConstructor
public class DealRestBitrixDto {

    @JsonProperty(value = "ID", access = JsonProperty.Access.WRITE_ONLY)
    private long id;

    @JsonProperty(value = "OPPORTUNITY", access = JsonProperty.Access.WRITE_ONLY)
    private float sumPrice;

    @JsonProperty(value = "STAGE_ID")
    private String stage;

    @JsonProperty(value = "CURRENCY_ID", access = JsonProperty.Access.WRITE_ONLY)
    private String currency;

    @JsonProperty(value = "CONTACT_ID", access = JsonProperty.Access.WRITE_ONLY)
    private int contactId;

    @JsonProperty(value = "ASSIGNED_BY_ID", access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;//

    @JsonProperty(value = "COMMENTS", access = JsonProperty.Access.WRITE_ONLY)
    private String comment;

    @JsonProperty(value = "UF_CRM_1575820479003", access = JsonProperty.Access.WRITE_ONLY)
    private String paymentOptionId;

    @JsonProperty(value = "UF_CRM_TTN_NUM", access = JsonProperty.Access.WRITE_ONLY)
    private String documentNumber;

    @JsonProperty(value = "UF_CRM_1575820544163")
    private String rozetkaFlag;

    @JsonProperty(value = "UF_CRM_1575820572770")
    private String pickupFlag;

    @JsonProperty(value = "UF_CRM_1575821896038")
    private String storeProcCalcFlag;

    @JsonProperty(value = "UF_CRM_1575821951932")
    private String previousStage;

    @JsonProperty(value = "UF_CRM_1581868087236")
    private String wholeSalePriceCalcFlag;

    @JsonProperty(value = "UF_CRM_1581868435096")
    private String printReceiptFlag;
    //    ///////////////////////////////////
    @JsonProperty(value = "UF_CRM_1575820621129")
    private String expectedDealSalaryBonus;

    @JsonProperty(value = "UF_CRM_1575820635355")
    private String actualDealSalaryBonus;

}