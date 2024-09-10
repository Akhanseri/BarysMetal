package com.example.barysmetal.controller;

import com.example.barysmetal.dtos.ApplicationRequestDto;
import com.example.barysmetal.model.Application;
import com.example.barysmetal.repository.ApplicationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/application")
@CrossOrigin(origins = "http://localhost:5173")
public class ApplicationController {
    private final ApplicationRepository applicationRepository;
    public ApplicationController(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    public ResponseEntity<List<Application>> getAllApplications() {
        List<Application> applications = applicationRepository.findAll();
        return new ResponseEntity<>(applications, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody ApplicationRequestDto applicationDto) {
        Application application = new Application();
        application.setFullName(applicationDto.getFullName());
        application.setNumber(applicationDto.getNumber());
        application.setComment(applicationDto.getComment()); // Optional field

        Application createdApplication = applicationRepository.save(application);
        return new ResponseEntity<>(createdApplication, HttpStatus.CREATED);
    }



}
