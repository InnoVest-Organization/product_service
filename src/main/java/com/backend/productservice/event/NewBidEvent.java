package com.backend.productservice.event;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NewBidEvent {
    private List<String> emails;
    private Long inventionId;
    private String productDescription;

    private LocalDate bidStartDate;
    private LocalTime bidStartTime;
    private LocalTime bidEndTime;
}
