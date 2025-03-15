package com.backend.productservice.controller;

import com.backend.productservice.dto.InventionRequest;
import com.backend.productservice.entity.Invention;
import com.backend.productservice.service.InventionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
