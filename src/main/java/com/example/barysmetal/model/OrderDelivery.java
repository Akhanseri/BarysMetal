package com.example.barysmetal.model;

import com.example.barysmetal.model.enums.DeliveryType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private int floor;

    private String comment; // Nullable

    // Getters and setters
}

