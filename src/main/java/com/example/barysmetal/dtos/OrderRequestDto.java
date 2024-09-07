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

    private String deliveryType; // Добавлено поле для типа доставки


    private Long deliveryId;
    private PaymentType paymentType;
    private Long recipientId;
    private List<Long> productIds;

    private String deliveryAddress;
    private int deliveryFloor;
    private String deliveryComment;

    private String recipientFullName;
    private String recipientPhone;
    private String recipientEmail;
    private String recipientCompany;
    private String recipientComment;
}
