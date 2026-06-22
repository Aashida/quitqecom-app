// UserController.java
package com.quitqecom.controller;

import com.quitqecom.dto.AdminRegisterDto;
import com.quitqecom.dto.CustomerRegisterDto;
import com.quitqecom.dto.RegisterResponseDto;
import com.quitqecom.dto.SellerRegisterDto;
import com.quitqecom.model.User;
import com.quitqecom.service.CustomerService;
import com.quitqecom.service.SellerService;
import com.quitqecom.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;
    private final CustomerService customerService;
    private final SellerService sellerService;

    // PUBLIC - Customer Registration
    @PostMapping("/customer")
    public ResponseEntity<RegisterResponseDto> registerCustomer(
            @Valid @RequestBody CustomerRegisterDto dto) {

        RegisterResponseDto response = userService.registerCustomer(dto);

        // Create customer profile after user registration
        User user = (User) userService.loadUserByUsername(dto.username());
        customerService.createCustomer(dto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUBLIC - Seller Registration
    @PostMapping("/seller")
    public ResponseEntity<RegisterResponseDto> registerSeller(
            @Valid @RequestBody SellerRegisterDto dto) {

        RegisterResponseDto response = userService.registerSeller(dto);

        // Create seller profile after user registration
        User user = (User) userService.loadUserByUsername(dto.username());
        sellerService.createSeller(dto, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PROTECTED - Admin Creation (only by existing ADMIN)
    @PostMapping("/admin")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RegisterResponseDto> createAdmin(
            @Valid @RequestBody AdminRegisterDto dto) {

        RegisterResponseDto response = userService.createAdmin(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}