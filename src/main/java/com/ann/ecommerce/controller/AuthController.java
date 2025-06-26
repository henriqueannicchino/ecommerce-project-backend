package com.ann.ecommerce.controller;

import com.ann.ecommerce.dto.AuthResponse;
import com.ann.ecommerce.dto.RegisterDTO;
import com.ann.ecommerce.entity.Customer;
import com.ann.ecommerce.security.JwtService;
import com.ann.ecommerce.dao.CustomerRepository;
import com.ann.ecommerce.dto.AuthRequest;
import com.ann.ecommerce.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterDTO dto) {
        if (this.customerRepository.findByEmail(dto.getEmail()) != null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody @Valid AuthRequest authRequest) {

        try {
            if (this.customerRepository.findByEmail(authRequest.getEmail()) == null)
                return ResponseEntity.badRequest().build();

            var usernamePassword = new UsernamePasswordAuthenticationToken(authRequest.getEmail(),
                    authRequest.getPassword());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            var token = jwtService.generateToken((Customer) auth.getPrincipal());

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception ex) {
            System.err.println("error on signin" + ex);
            return ResponseEntity.internalServerError().build();
        }
    }
}
