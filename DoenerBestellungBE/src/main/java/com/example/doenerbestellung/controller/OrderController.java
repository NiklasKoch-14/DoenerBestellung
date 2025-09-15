package com.example.doenerbestellung.controller;

import com.example.doenerbestellung.dto.OrderDTO;
import com.example.doenerbestellung.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(method = RequestMethod.GET, value = "/orders")
    public List<OrderDTO> order() {
        return this.orderService.order();
    }


}
