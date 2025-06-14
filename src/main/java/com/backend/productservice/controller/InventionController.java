package com.backend.productservice.controller;

import com.backend.productservice.dto.InventionRequest;
import com.backend.productservice.dto.BidTimeUpdateRequest;
import com.backend.productservice.dto.BidSelectionRequest;
import com.backend.productservice.dto.InnovatorDetailsResponse;
import com.backend.productservice.entity.Invention;
import com.backend.productservice.service.InventionService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventions")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}) // Add this line
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


    @GetMapping("/inventor/{inventorId}")
    public ResponseEntity<List<Invention>> getInventionsByInventorId(@PathVariable Long inventorId) {
        List<Invention> inventions = inventionService.getInventionsByInventorId(inventorId);
        return ResponseEntity.ok(inventions);
    }
    
    @PostMapping("/bidSelected")
    public ResponseEntity<String> updateBidSelection(@RequestBody BidSelectionRequest request) {
        boolean isUpdated = inventionService.selectBid(
                request.getInventionId(),
                request.getInvestorId(),
                request.getIsLive());

        if (isUpdated) {
            return ResponseEntity.ok("Bid selection updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invention ID not found!");
        }
    }
}