package com.backend.productservice.dto;

import com.backend.productservice.entity.ModeOfSale;
import com.backend.productservice.entity.PaymentPackage;
import lombok.Data;

import java.sql.Time;

@Data
public class InventionRequest {
    private Long inventionId;
    private Long inventorId;
    private Long investorId;
    private String productVideo;
    private String productDescription;
    private Integer capital;
    private String salesData;
    private ModeOfSale modeOfSale;
    private String costDescription;
    private Integer expectedCapital;
    private Integer breakupRevenue;
    private PaymentPackage paymentPackage;
    private Time bidStartTime;
    private Time bidEndTime;
    private String aoi;
}
