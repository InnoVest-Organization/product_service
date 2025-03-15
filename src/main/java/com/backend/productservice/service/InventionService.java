package com.backend.productservice.service;

import com.backend.productservice.dto.InventionRequest;
import com.backend.productservice.entity.Invention;
import com.backend.productservice.repository.InventionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventionService {

    private final InventionRepository inventionRepository;

    public Invention saveInvention(InventionRequest request) {
        Invention invention = Invention.builder()
                .inventionId(request.getInventionId())
                .inventorId(request.getInventorId())
                .investorId(request.getInvestorId())
                .productVideo(request.getProductVideo())
                .productDescription(request.getProductDescription())
                .capital(request.getCapital())
                .salesData(request.getSalesData()) // Directly setting List<Integer>
                .modeOfSale(request.getModeOfSale())
                .costDescription(request.getCostDescription())
                .expectedCapital(request.getExpectedCapital())
                .breakupRevenue(request.getBreakupRevenue())
                .paymentPackage(request.getPaymentPackage())
                .bidStartTime(request.getBidStartTime())
                .bidEndTime(request.getBidEndTime())
                .aoi(request.getAoi())
                .bidStartDate(request.getBidStartDate())
                .isLive(request.getIsLive())
                .isPaid(request.getIsPaid())
                .build();

        return inventionRepository.save(invention);
    }

    public Invention getInventionByInnovationId(Long innovationId) {
        return inventionRepository.findByInventionId(innovationId)
                .orElseThrow(() -> new RuntimeException("Invention not found with innovationId: " + innovationId));
    }

}
