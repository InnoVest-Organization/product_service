package com.backend.productservice.service;

import com.backend.productservice.dto.InventionRequest;
import com.backend.productservice.dto.InnovatorDetailsResponse;
import com.backend.productservice.entity.Invention;
import com.backend.productservice.repository.InventionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventionService {

    private final InventionRepository inventionRepository;
    private final ExternalApiService externalApiService;

    public Invention saveInvention(InventionRequest request) {
        Invention invention = Invention.builder()
                .inventionId(request.getInventionId())
                .inventorId(request.getInventorId())
                .investorId(request.getInvestorId())
                .productVideo(request.getProductVideo())
                .productDescription(request.getProductDescription())
                .capital(request.getCapital())
                .salesData(request.getSalesData())
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

    public boolean updateBidTimes(Long inventionId, LocalTime bidStartTime, LocalTime bidEndTime,
            LocalDate bidStartDate) {
        Optional<Invention> inventionOpt = inventionRepository.findById(inventionId);

        if (inventionOpt.isPresent()) {
            Invention invention = inventionOpt.get();
            invention.setBidStartTime(bidStartTime);
            invention.setBidEndTime(bidEndTime);
            invention.setBidStartDate(bidStartDate);
            inventionRepository.save(invention);

            try {
                List<String> investorEmails = externalApiService.getInvestorEmails(
                        inventionId,
                        invention.getAoi(),
                        invention.getPaymentPackage().name());
                System.out.println(investorEmails);
                String res = externalApiService.sendNotifications(investorEmails, inventionId,
                        invention.getProductDescription(), bidStartDate, bidStartTime, bidEndTime);
                System.out.println(res);
            } catch (Exception e) {
                log.error("Error while processing external service calls", e);

            }

            return true;
        } else {
            return false;
        }
    }

    public InnovatorDetailsResponse getInnovatorDetails(Long inventionId) {
        Invention invention = inventionRepository.findById(inventionId)
                .orElseThrow(() -> new RuntimeException("Invention not found with id: " + inventionId));

        String innovatorEmail = externalApiService.getInnovatorEmail(invention.getInventorId());

        return new InnovatorDetailsResponse(
                innovatorEmail,
                invention.getPaymentPackage().name());
    }

    /**
     * Select a bid for an invention, updating the investor ID and live status.
     * Only updates these two fields to avoid issues with date/time fields.
     *
     * @param inventionId The ID of the invention to update
     * @param investorId The ID of the selected investor
     * @param isLive The new live status (typically false after selection)
     * @return true if update succeeded, false if invention not found
     */
    public boolean selectBid(Long inventionId, Long investorId, Boolean isLive) {
        try {
            Optional<Invention> inventionOpt = inventionRepository.findByInventionId(inventionId);
            
            if (inventionOpt.isPresent()) {
                Invention invention = inventionOpt.get();
                
                // Create a new invention object and copy only the required fields
                Invention partialUpdate = new Invention();
                partialUpdate.setInventionId(invention.getInventionId());
                partialUpdate.setInvestorId(investorId);
                partialUpdate.setIsLive(isLive);
                
                // Only update specific fields in the database using a custom repository method
                // This avoids the issue with time/date fields
                inventionRepository.save(partialUpdate);
                
                log.info("Successfully updated bid selection for invention: {}", inventionId);
                return true;
            } else {
                log.warn("No invention found with ID: {}", inventionId);
                return false;
            }
        } catch (Exception e) {
            log.error("Error updating bid selection for invention: {}", inventionId, e);
            throw new RuntimeException("Error selecting bid: " + e.getMessage(), e);
        }
    }

    public List<Invention> getInventionsByInventorId(Long inventorId) {
        return inventionRepository.findByInventorId(inventorId);
    }

}
