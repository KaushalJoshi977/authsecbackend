package com.realnet;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiCaller {
    private final RestTemplate restTemplate;

    public ApiCaller() {
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<String> callApiWithJwtToken(String apiUrl, String jwtToken) {
        // Set the JWT token in the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        // Create an HttpEntity with the headers
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Make the API request
        return restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
    }
}
