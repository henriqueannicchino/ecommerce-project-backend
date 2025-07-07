package com.ann.ecommerce.dto;

import com.ann.ecommerce.entity.Address;
import com.ann.ecommerce.entity.Order;
import com.ann.ecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class PurchaseRequest {

    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
