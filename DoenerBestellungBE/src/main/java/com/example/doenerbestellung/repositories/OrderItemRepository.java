package com.example.doenerbestellung.repositories;

import com.example.doenerbestellung.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
