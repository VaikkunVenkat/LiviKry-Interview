package com.example.backend.services;

import com.example.backend.model.Services;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
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
      // setup headers
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<>(headers);
      
      // iterate over services here:
      for (Services service: iterable) {
        ResponseEntity<Void> response = this.restTemplate.exchange(service.getUrl(), HttpMethod.GET, entity, Void.class);
        service.setStatus(response.getStatusCode().getReasonPhrase());
      }

      return iterable;
  }

  public Services fetchServiceWithStatus(Services service) {
    // setup headers
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<>(headers);

    ResponseEntity<Void> response = this.restTemplate.exchange(service.getUrl(), HttpMethod.GET, entity, Void.class);
    service.setStatus(response.getStatusCode().getReasonPhrase());

    return service;
  }
}
