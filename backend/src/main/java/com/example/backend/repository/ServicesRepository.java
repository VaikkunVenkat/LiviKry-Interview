package com.example.backend.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository; 
import com.example.backend.model.Services; 

@Repository
public interface ServicesRepository extends CrudRepository <Services, Long> { }
