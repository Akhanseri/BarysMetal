package com.example.barysmetal.repository;
import com.example.barysmetal.model.OrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, Long> {
}
