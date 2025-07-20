package com.ann.ecommerce.controller;

import com.ann.ecommerce.dao.OrderRepository;
import com.ann.ecommerce.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

     @GetMapping("/findByCustomerId")
    public Page<Order> findByCustomerId(@RequestParam("customer_id") Long customerId, Pageable pageable) {
        return orderRepository.findByCustomerId(customerId, pageable);
    }
}
