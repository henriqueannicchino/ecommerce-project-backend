package com.ann.ecommerce.service;

import com.ann.ecommerce.dto.PurchaseRequest;
import com.ann.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(PurchaseRequest purchase);
}
