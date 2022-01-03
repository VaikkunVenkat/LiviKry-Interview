package com.example.backend.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.model.Services;
import com.example.backend.repository.ServicesRepository;
import com.example.backend.services.RestService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class ServicesController {

  @Autowired
  ServicesRepository servicesRepository;

  @Autowired
  RestService restService;
  
  
  @GetMapping("/refreshServices")
  public List<Services> refreshAllServices() {
    List<Services> servicesList = new ArrayList<Services>();
    Iterable<Services> iterable = servicesRepository.findAll();
    Iterable<Services> iterableUpdated = restService.getServiceStatuses(iterable);
    iterableUpdated.forEach(servicesList::add);
    return servicesList; 
  }

  @GetMapping("/services")
  public List<Services> getAllServices() {
    List<Services> servicesList = new ArrayList<Services>();
    Iterable<Services> iterable = servicesRepository.findAll();
    iterable.forEach(servicesList::add);
    return servicesList;
  }

  @GetMapping("/services/{id}")
  public ResponseEntity<Services> getServicesById(@PathVariable(value = "id") Integer id) {
    Optional<Services> services = servicesRepository.findById(id);
  return services.isPresent() ? new ResponseEntity<Services>(services.get(), HttpStatus.OK)
      : new ResponseEntity("No data found", HttpStatus.NOT_FOUND);
  }

  @PostMapping("/services")
  public ResponseEntity<Services> createServices(@RequestBody Services service) {
    Services serviceWithStatus = restService.fetchServiceWithStatus(service);
    Services prod = servicesRepository.save(serviceWithStatus);
    return ResponseEntity.created(URI.create(String.format("/services/%s", serviceWithStatus.getId()))).body(prod);
  }

  @PutMapping("/services/{id}")
  public ResponseEntity<Services> updateservices(@PathVariable(value = "id") Integer id, @RequestBody Services newservices) {
    Optional<Services> services = servicesRepository.findById(id);
  if (services.isPresent()) {
    Services prod = services.get();
    prod.setName(newservices.getName());
    prod.setUrl(newservices.getUrl());
    prod.setStatus(restService.fetchServiceWithStatus(newservices).getStatus());
    prod = servicesRepository.save(prod);
    return ResponseEntity.ok().body(prod);
    } else {
    return ResponseEntity.notFound().build();
    }
  }

  @DeleteMapping("/services/{id}")
  public ResponseEntity<Services> deleteservices(@PathVariable(value = "id") Integer id) {
    Optional<Services> services = servicesRepository.findById(id);
    if (services.isPresent()) {
    servicesRepository.delete(services.get());
    return new ResponseEntity("services has been deleted successfully.", HttpStatus.NO_CONTENT);
    } else {
    return ResponseEntity.notFound().build();
    }
  }
}