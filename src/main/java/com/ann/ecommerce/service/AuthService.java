package com.ann.ecommerce.service;

import com.ann.ecommerce.dto.AuthResponse;
import com.ann.ecommerce.dto.RegisterDTO;
import com.ann.ecommerce.entity.Customer;
import com.ann.ecommerce.security.JwtService;
import com.ann.ecommerce.dao.CustomerRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


//import java.util.Collections;

@Service
public class AuthService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JwtService jwtService;


//     private final UserRepository userRepository;
//     private final PasswordEncoder passwordEncoder;
//     private final JwtService jwtService;
//     private final AuthenticationManager authenticationManager;

//     public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
//         this.userRepository = userRepository;
//         this.passwordEncoder = passwordEncoder;
//         this.jwtService = jwtService;
//         this.authenticationManager = authenticationManager;
//     }

    public AuthResponse register(RegisterDTO dto) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        Customer newCustomer = new Customer(dto.getFirstName(), dto.getLastName(), dto.getEmail(), encryptedPassword, dto.getRoles());
        this.customerRepository.save(newCustomer);

        var jwtToken = jwtService.generateToken(newCustomer);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    // public AuthResponse authenticate(AuthRequest request, UserDetails customer) {
    //     authenticationManager.authenticate(
    //             new UsernamePasswordAuthenticationToken(
    //                     request.getEmail(),
    //                     request.getPassword()
    //             )
    //     );
    //     var jwtToken = jwtService.generateToken(customer.getUsername());
    //     return AuthResponse.builder()
    //             .token(jwtToken)
    //             .build();
    // }
}