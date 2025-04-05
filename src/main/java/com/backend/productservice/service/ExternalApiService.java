package com.backend.productservice.service;

import com.backend.productservice.dto.InvestorMatchRequest;
import com.backend.productservice.dto.NotificationRequest;
import com.backend.productservice.event.NewBidEvent;
import com.backend.productservice.dto.InnovatorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.messaging.support.MessageBuilder;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;

@Service
@RequiredArgsConstructor
public class ExternalApiService {
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, NewBidEvent> kafkaTemplate;

    @CircuitBreaker(name = "invention", fallbackMethod = "getInvestorEmailsFallback")
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

    private List<String> getInvestorEmailsFallback(Long inventionId, List<String> aoi, String paymentPackage,
            Exception ex) {
        return new ArrayList<>();
    }

    public String sendNotifications(List<String> emails, Long inventionId, String productDescription,
            LocalDate bidStartDate, LocalTime bidStartTime, LocalTime bidEndTime) {

        NewBidEvent newbid = new NewBidEvent(emails, inventionId, productDescription, bidStartDate, bidStartTime,
                bidEndTime);
        ProducerRecord<String, NewBidEvent> record = new ProducerRecord<>("new-bid", newbid);
        record.headers().add(new RecordHeader("type", "newBid".getBytes()));

        kafkaTemplate.send(record);

        System.out.println("Sent the message");
        // String notificationServiceUrl =
        // "http://localhost:5005/api/notifications/newbid";

        // NotificationRequest request = new NotificationRequest(emails, inventionId,
        // productDescription, bidStartDate,
        // bidStartTime, bidEndTime);
        // ResponseEntity<String> res =
        // restTemplate.postForEntity(notificationServiceUrl, request, String.class);
        return "Added to the kafka topic";
    }

    public String getInnovatorEmail(Long innovatorId) {
        String innovatorServiceUrl = "http://localhost:5001/api/innovator/" + innovatorId;
        ResponseEntity<InnovatorResponse> response = restTemplate.getForEntity(
                innovatorServiceUrl,
                InnovatorResponse.class);
        return response.getBody().getEmail();
    }
}