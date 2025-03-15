package com.backend.productservice.dto;

import com.backend.productservice.entity.ModeOfSale;
import com.backend.productservice.entity.PaymentPackage;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class InventionRequest {
    private Long inventionId;
    private Long inventorId;
    private Long investorId;
    private String productVideo;
    private String productDescription;
    private Integer capital;
    private List<Integer> salesData; // Changed from String to List<Integer>
    private ModeOfSale modeOfSale;
    private String costDescription;
    private Integer expectedCapital;
    private Integer breakupRevenue;
    private PaymentPackage paymentPackage;
    private LocalTime bidStartTime; // Changed from Time to LocalTime
    private LocalTime bidEndTime; // Changed from Time to LocalTime
    private List<String> aoi; // Changed from String to List<String>
}
