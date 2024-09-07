package com.example.barysmetal.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long orderId;
    private LocalDateTime orderTime;
    private String deliveryType;
    private String deliveryAddress;
    private String recipientName;
    private List<String> productNames;
}
