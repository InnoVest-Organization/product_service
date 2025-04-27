package com.backend.productservice.controller;

import com.backend.productservice.dto.InventionRequest;
import com.backend.productservice.dto.BidTimeUpdateRequest;
import com.backend.productservice.dto.InnovatorDetailsResponse;
import com.backend.productservice.entity.Invention;
import com.backend.productservice.service.InventionService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/inventions")
@RequiredArgsConstructor
public class InventionController {

    private final InventionService inventionService;

    @PostMapping
    public ResponseEntity<Invention> createInvention(@RequestBody InventionRequest request) {
        Invention savedInvention = inventionService.saveInvention(request);
        System.out.println("Saved invention: " + savedInvention);
        return ResponseEntity.ok(savedInvention);
    }

    @GetMapping("/{innovationId}")
    public ResponseEntity<Invention> getInventionByInnovationId(@PathVariable("innovationId") Long innovationId) {
        Invention invention = inventionService.getInventionByInnovationId(innovationId);
        return ResponseEntity.ok(invention);
    }

    @PutMapping("/updateBidTimes")
    public ResponseEntity<String> updateBidTimes(@RequestBody BidTimeUpdateRequest request) {
        boolean isUpdated = inventionService.updateBidTimes(
                request.getInventionId(),
                request.getBidStartTime(),
                request.getBidEndTime(),
                request.getBidStartDate());

        if (isUpdated) {
            return ResponseEntity.ok("Bid times updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invention ID not found!");
        }
    }

    @GetMapping("/innovator-details/{inventionId}")
    public ResponseEntity<InnovatorDetailsResponse> getInnovatorDetails(@PathVariable Long inventionId) {
        return ResponseEntity.ok(inventionService.getInnovatorDetails(inventionId));
    }


    @PatchMapping("/{inventionId}/update-investor")
    public ResponseEntity<String> updateInvestor(
            @PathVariable Long inventionId,
            @RequestParam(required = false) Long investorId,
            @RequestBody(required = false) Map<String, Long> requestBody) {
        
        // Get investorId either from request param or request body
        Long finalInvestorId = investorId;
        if (finalInvestorId == null && requestBody != null && requestBody.containsKey("investorId")) {
            finalInvestorId = requestBody.get("investorId");
        }
        
        if (finalInvestorId == null) {
            return ResponseEntity.badRequest().body("InvestorId is required!");
        }
        
        boolean isUpdated = inventionService.updateInvestor(inventionId, finalInvestorId);
        
        if (isUpdated) {
            return ResponseEntity.ok("Investor updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invention ID not found!");
        }
    }
}