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

  public HttpStatus getServiceStatuses() {
      // parse ArrayList of services from db.
      String url = "https://jsonplaceholder.typicode.com/posts";
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
      HttpEntity<String> entity = new HttpEntity<>(headers);
      ResponseEntity response = this.restTemplate.exchange(url, HttpMethod.GET, entity, Void.class);

      return response.getStatusCode();
  }

/*   public Post updatePostWithResponse() {
      String url = "https://jsonplaceholder.typicode.com/posts/{id}";

      // create headers
      HttpHeaders headers = new HttpHeaders();
      // set `content-type` header
      headers.setContentType(MediaType.APPLICATION_JSON);
      // set `accept` header
      headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

      // create a post object
      Post post = new Post(4, "New Title", "New Body");

      // build the request
      HttpEntity<Post> entity = new HttpEntity<>(post, headers);
      

      // send PUT request to update post with `id` 10
      ResponseEntity<Post> response = this.restTemplate.exchange(url, HttpMethod.PUT, entity, Post.class, 10);

      // check response status code
      if (response.getStatusCode() == HttpStatus.OK) {
          return response.getBody();
      } else {
          return null;
      }
  }

  public void deletePost() {
      String url = "https://jsonplaceholder.typicode.com/posts/{id}";

      // send DELETE request to delete post with `id` 10
      this.restTemplate.delete(url, 10);
  }

  public HttpHeaders retrieveHeaders() {
      String url = "https://jsonplaceholder.typicode.com/posts";

      // send HEAD request
      return this.restTemplate.headForHeaders(url);
  }

  public Set<HttpMethod> allowedOperations() {
      String url = "https://jsonplaceholder.typicode.com/posts";

      // send HEAD request
      return this.restTemplate.optionsForAllow(url);
  }

  public String unknownRequest() {
      try {
          String url = "https://jsonplaceholder.typicode.com/404";
          return this.restTemplate.getForObject(url, String.class);
      } catch (HttpStatusCodeException ex) {
          // raw http status code e.g `404`
          System.out.println(ex.getRawStatusCode());
          // http status code e.g. `404 NOT_FOUND`
          System.out.println(ex.getStatusCode().toString());
          // get response body
          System.out.println(ex.getResponseBodyAsString());
          // get http headers
          HttpHeaders headers = ex.getResponseHeaders();
          System.out.println(headers.get("Content-Type"));
          System.out.println(headers.get("Server"));
      }

      return null;
  } */
}
