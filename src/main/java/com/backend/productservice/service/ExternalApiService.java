package com.backend.productservice.service;

import com.backend.productservice.dto.InvestorMatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalApiService {
    private final RestTemplate restTemplate;

    public List<String> getInvestorEmails(Long inventionId, List<String> aoi, String paymentPackage) {
        String investorServiceUrl = "http://localhost:5006/api/investors/match";

        InvestorMatchRequest request = new InvestorMatchRequest(inventionId, aoi, paymentPackage);

        ResponseEntity<List<String>> response = restTemplate.exchange(
                investorServiceUrl,
                HttpMethod.POST,
                new HttpEntity<>(request),
                new ParameterizedTypeReference<List<String>>() {
                });

        return response.getBody();
    }

    public void sendNotifications(List<String> emails) {
        String notificationServiceUrl = "http://localhost:5005/api/notifications";
        restTemplate.postForEntity(notificationServiceUrl, emails, Void.class);
    }
}