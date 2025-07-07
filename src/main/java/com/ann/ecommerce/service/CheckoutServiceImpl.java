package com.ann.ecommerce.service;

import com.ann.ecommerce.dao.CustomerRepository;
import com.ann.ecommerce.dto.PurchaseRequest;
import com.ann.ecommerce.dto.PurchaseResponse;
import com.ann.ecommerce.entity.Customer;
import com.ann.ecommerce.entity.Order;
import com.ann.ecommerce.entity.OrderItem;
import com.ann.ecommerce.security.JwtService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Autowired
    JwtService jwtService;
    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(PurchaseRequest purchase, String token) {
        try {
        var email = jwtService.validateToken(token);

        // retrieve the order info from dto
        Order order = purchase.getOrder();

        // generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        // populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        // populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        // populate customer with order
        Customer customer = customerRepository.getByEmail(email)
        .orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.add(order);

        // save to the database
        customerRepository.save(customer);

        // return a response
        return new PurchaseResponse(orderTrackingNumber);
        } catch (Exception ex) {
            System.err.println("error on place order" + ex);
            return new PurchaseResponse("");
        }
    }

    private String generateOrderTrackingNumber() {
        // generate a random (UUID version-4)
        return UUID.randomUUID().toString();
    }
}
