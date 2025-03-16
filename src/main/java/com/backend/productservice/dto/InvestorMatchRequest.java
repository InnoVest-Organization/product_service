package com.backend.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestorMatchRequest {
    private Long inventionId;
    private List<String> aoi;
    private String paymentPackage;
}