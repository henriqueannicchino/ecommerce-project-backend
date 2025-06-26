package com.ann.ecommerce.dto;

import com.ann.ecommerce.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private UserRole roles;
}