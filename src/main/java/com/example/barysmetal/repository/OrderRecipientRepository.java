package com.example.barysmetal.repository;

import com.example.barysmetal.model.OrderRecipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRecipientRepository extends JpaRepository<OrderRecipient, Long> {
}
