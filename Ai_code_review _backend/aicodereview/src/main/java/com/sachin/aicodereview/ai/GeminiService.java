package com.sachin.aicodereview.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

@Service
public class GeminiService {


    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeminiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String reviewCode(String code) {

        String url =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash-lite:generateContent?key="
                        + apiKey;

        String prompt = """
You are a Senior Software Engineer and Code Reviewer.

Analyze the given code thoroughly and provide feedback in the following format:

# Code Review Report

## 1. Overall Summary
Provide a brief summary of what the code does.

## 2. Bugs & Issues
List any bugs, errors, logical mistakes, or potential failures.

## 3. Security Issues
Identify security vulnerabilities, unsafe coding practices, or attack vectors.

## 4. Performance Analysis
Point out performance bottlenecks and optimization opportunities.

## 5. Code Smells
Identify bad naming, duplicated code, poor structure, magic numbers, etc.

## 6. Best Practices
Mention industry-standard best practices that are missing.

## 7. Suggested Improvements
Provide clear actionable improvements.

## 8. Refactored Code
Provide an improved version of the code.

## 9. Score
Give a score out of 10.

Rules:
- Be strict but constructive.
- Explain each issue clearly.
- Use markdown formatting.
- Focus on readability, maintainability, security, and performance.

Code:
""" + code;


        Map<String, Object> textPart = Map.of(
                "text", prompt
        );

        Map<String, Object> content = Map.of(
                "parts", List.of(textPart)
        );

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(content)
        );

        try {
            String response = restTemplate.postForObject(
                    url,
                    requestBody,
                    String.class
            );

            if (response == null || response.isBlank()) {
                return "Gemini returned an empty response. Please try again.";
            }

            JsonNode root = objectMapper.readTree(response);

            String review = root
                    .path("candidates")
                    .path(0)
                    .path("content")
                    .path("parts")
                    .path(0)
                    .path("text")
                    .asText("");

            if (review.isBlank()) {
                return "Gemini did not return review text. Please try again.";
            }

            return review;

        } catch (Exception e) {

            return """
        Gemini API is busy right now.

        Please try again after a few minutes.
        """;
        }
    }


}
