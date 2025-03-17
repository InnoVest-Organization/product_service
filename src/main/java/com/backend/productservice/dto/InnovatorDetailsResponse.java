package com.backend.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InnovatorDetailsResponse {
    private String innovatorEmail;
    private String paymentPackage;
}