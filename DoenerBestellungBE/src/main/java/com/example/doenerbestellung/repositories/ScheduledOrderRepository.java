package com.example.doenerbestellung.repositories;

import com.example.doenerbestellung.entity.ScheduledOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduledOrderRepository extends JpaRepository<ScheduledOrder, Long> {
}
