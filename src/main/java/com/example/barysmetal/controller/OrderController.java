package com.example.barysmetal.controller;

import com.example.barysmetal.dtos.OrderRequestDto;
import com.example.barysmetal.dtos.OrderResponseDto;
import com.example.barysmetal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        try {
            OrderResponseDto orderResponse = orderService.createOrder(orderRequestDto);
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}

