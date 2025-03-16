package com.backend.productservice.service;

import com.backend.productservice.dto.InvestorMatchRequest;
import com.backend.productservice.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

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

    public String sendNotifications(List<String> emails, Long inventionId, String productDescription,
            LocalDate bidStartDate, LocalTime bidStartTime, LocalTime bidEndTime) {
        String notificationServiceUrl = "http://localhost:5005/api/notifications";

        NotificationRequest request = new NotificationRequest(emails, inventionId, productDescription, bidStartDate,
                bidStartTime, bidEndTime);
        ResponseEntity<String> res = restTemplate.postForEntity(notificationServiceUrl, request, String.class);
        return res.getBody();
    }
}