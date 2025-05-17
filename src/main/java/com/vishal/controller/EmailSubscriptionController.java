package com.vishal.controller;

import com.vishal.dto.EmailSubscriptionRequest;
import com.vishal.model.EmailSubscriber;
import com.vishal.repository.EmailSubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
public class EmailSubscriptionController {

    private final EmailSubscriberRepository subscriberRepository;

    public EmailSubscriptionController(EmailSubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestBody EmailSubscriptionRequest request) {
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }

        if (subscriberRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already subscribed");
        }

        EmailSubscriber subscriber = new EmailSubscriber(request.getEmail());
        subscriberRepository.save(subscriber);

        return ResponseEntity.ok("Subscribed successfully");
    }
}
