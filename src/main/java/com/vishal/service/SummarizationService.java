package com.vishal.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SummarizationService {

    @Value("${huggingface.apiToken}")
    private String apiToken;

    @Value("${huggingface.modelUrl}")
    private String modelUrl;

    private final RestTemplate restTemplate;

    public SummarizationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String summarize(String text) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            Map<String, Object> body = new HashMap<>();
            body.put("inputs", text);
            body.put("parameters", Collections.singletonMap("max_length", 200));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(modelUrl, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                
                String json = response.getBody();
                int start = json.indexOf("summary_text") + 15;
                int end = json.indexOf("\"", start);
                return json.substring(start, end);
            } else {
                return "Failed to summarize news: " + response.getStatusCode();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception during summarization: " + e.getMessage();
        }
    }
}