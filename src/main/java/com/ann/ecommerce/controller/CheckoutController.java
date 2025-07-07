package com.ann.ecommerce.controller;

import com.ann.ecommerce.dto.PurchaseRequest;
import com.ann.ecommerce.dto.PurchaseResponse;
import com.ann.ecommerce.service.CheckoutService;

import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody PurchaseRequest purchase,
            @RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);

        return checkoutService.placeOrder(purchase, token);
    }
}
