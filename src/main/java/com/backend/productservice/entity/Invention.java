package com.backend.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Time;

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
    private String productDescription;
    private Integer capital;

    private String salesData;

    @Enumerated(EnumType.STRING)
    private ModeOfSale modeOfSale;

    private String costDescription;
    private Integer expectedCapital;
    private Integer breakupRevenue;

    @Enumerated(EnumType.STRING)
    private PaymentPackage paymentPackage;

    private Time bidStartTime;
    private Time bidEndTime;

    private String aoi;
}
