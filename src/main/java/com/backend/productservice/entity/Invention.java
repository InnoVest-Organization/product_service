package com.backend.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "invention_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invention {

    @Id

    private Long inventionId;

    @Column(nullable = false)
    private Long inventorId;

    @Column(nullable = false)
    private Long investorId;

    private String productVideo;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String productDescription;

    private Integer capital;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private List<Integer> salesData;

    @Enumerated(EnumType.STRING)
    private ModeOfSale modeOfSale;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String costDescription;

    private Integer expectedCapital;
    private Integer breakupRevenue;

    @Enumerated(EnumType.STRING)
    private PaymentPackage paymentPackage;

    private LocalTime bidStartTime;
    private LocalTime bidEndTime;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private List<String> aoi;
}
