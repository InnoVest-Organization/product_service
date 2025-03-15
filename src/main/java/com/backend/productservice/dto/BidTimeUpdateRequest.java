package com.backend.productservice.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BidTimeUpdateRequest {
    private Long inventionId;
    private LocalTime bidStartTime;
    private LocalTime bidEndTime;
    private LocalDate bidStartDate;
}
