package com.example.barysmetal.dtos;

import com.example.barysmetal.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequestDto {

    private String deliveryMethod; // Replaced deliveryType with deliveryMethod
    private PaymentType paymentMethod; // Renamed to paymentMethod
    private String deliveryAddress;
    private int deliveryFloor;
    private String deliveryComment;

    private String recipientFullName;
    private String recipientPhone;
    private String recipientEmail;
    private String recipientCompany;
    private List<Long> productIds; // Added product IDs for the order

}
