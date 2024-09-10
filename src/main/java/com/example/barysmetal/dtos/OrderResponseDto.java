package com.example.barysmetal.dtos;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private LocalDateTime time;
    private String deliveryType;
    private String deliveryAddress;
    private String recipientFullName;
    private List<String> productNames;
}