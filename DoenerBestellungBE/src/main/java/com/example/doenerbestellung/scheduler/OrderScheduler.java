package com.example.doenerbestellung.scheduler;

import com.example.doenerbestellung.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderScheduler {

    @Autowired
    private OrderService orderService;
    /*@Scheduled(cron="0 10 * * 5")*/
    @Scheduled(cron = "*/10 * * * * *")
    public void makeOrder() {

        this.orderService.order();



        log.info("MakeOrder");
    }
}
