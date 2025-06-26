package com.ann.ecommerce.dao;

import com.ann.ecommerce.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    UserDetails findByEmail(String email);
}
