package com.example.barysmetal.service;

import com.example.barysmetal.dtos.OrderRequestDto;
import com.example.barysmetal.dtos.OrderResponseDto;
import com.example.barysmetal.model.Order;
import com.example.barysmetal.model.OrderDelivery;
import com.example.barysmetal.model.OrderRecipient;
import com.example.barysmetal.model.Product;
import com.example.barysmetal.model.enums.DeliveryType;
import com.example.barysmetal.repository.OrderDeliveryRepository;
import com.example.barysmetal.repository.OrderRecipientRepository;
import com.example.barysmetal.repository.OrderRepository;
import com.example.barysmetal.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDeliveryRepository orderDeliveryRepository;

    @Autowired
    private OrderRecipientRepository orderRecipientRepository;

    @Autowired
    private ProductRepository productRepository;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        // Create recipient
        OrderRecipient recipient = new OrderRecipient();
        recipient.setFullName(orderRequestDto.getRecipientFullName());
        recipient.setPhone(orderRequestDto.getRecipientPhone());
        recipient.setEmail(orderRequestDto.getRecipientEmail());
        recipient.setCompany(orderRequestDto.getRecipientCompany());
        recipient = orderRecipientRepository.save(recipient);

        // Create delivery
        OrderDelivery delivery = new OrderDelivery();
        delivery.setType(DeliveryType.valueOf(orderRequestDto.getDeliveryMethod())); // Using deliveryMethod
        delivery.setAddress(orderRequestDto.getDeliveryAddress());
        delivery.setFloor(orderRequestDto.getDeliveryFloor());
        delivery.setComment(orderRequestDto.getDeliveryComment());
        delivery = orderDeliveryRepository.save(delivery);

        // Create order
        Order order = new Order();
        order.setRecipient(recipient);
        order.setDelivery(delivery);
        order.setPaymentType(orderRequestDto.getPaymentMethod()); // Using paymentMethod
        order.setTime(LocalDateTime.now());

        // Retrieve and set products (optional based on business logic)
        List<Product> products = productRepository.findAllById(orderRequestDto.getProductIds());
        order.setProducts(products);

        Order savedOrder = orderRepository.save(order);

        // Response
        return new OrderResponseDto(
                savedOrder.getId(),
                savedOrder.getTime(),
                savedOrder.getDelivery().getType().name(),
                savedOrder.getDelivery().getAddress(),
                savedOrder.getRecipient().getFullName(),
                products.stream().map(Product::getName).collect(Collectors.toList())
        );
    }

}

