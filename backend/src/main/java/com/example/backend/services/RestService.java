package com.example.backend.services;

import com.example.backend.model.Services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.*;

@Service
public class RestService {

 
  private final RestTemplate restTemplate;

  public RestService(RestTemplateBuilder restTemplateBuilder) {
      // set connection and read timeouts
      this.restTemplate = restTemplateBuilder
              .setConnectTimeout(Duration.ofSeconds(500))
              .setReadTimeout(Duration.ofSeconds(500))
              .build();
  }

  public Iterable<Services> getServiceStatuses(Iterable<Services> iterable) {

      // iterate over services here:
      for (Services service: iterable) {
        this.fetchServiceWithStatus(service);
      }

      return iterable;
  }

  public Services fetchServiceWithStatus(Services service) {
    // setup headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<>(headers);

    try {
      ResponseEntity<Void> response = this.restTemplate.exchange(service.getUrl(), HttpMethod.GET, entity, Void.class);
      service.setStatus(response.getStatusCode().getReasonPhrase());
    } catch ( HttpClientErrorException | HttpServerErrorException  httpClientError) {
      service.setStatus("FAIL");
    }

    return service;
  }
}
