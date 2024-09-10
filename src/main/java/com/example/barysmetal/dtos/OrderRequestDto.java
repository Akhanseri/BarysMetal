package com.example.barysmetal.dtos;

import com.example.barysmetal.model.enums.PaymentType;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDto {
    private String deliveryMethod;
    private String paymentMethod;
    private String deliveryAddress;
    private int deliveryFloor;
    private String deliveryComment;
    private String recipientFullName;
    private String recipientPhone;
    private String recipientEmail;
    private String recipientComment;
    private String recipientCompany;
    private List<Long> productIds;
}
