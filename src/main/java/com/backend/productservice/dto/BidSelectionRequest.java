package com.backend.productservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BidSelectionRequest {
    @JsonProperty("Invention_ID")
    private Long inventionId;
    
    @JsonProperty("Investor_ID")
    private Long investorId;
    
    @JsonProperty("Is_Live")
    private Boolean isLive;
}
