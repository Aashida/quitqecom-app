package com.quitqecom.controller;

import com.quitqecom.dto.CustomerProfileRespDto;
import com.quitqecom.dto.CustomerUpdateDto;
import com.quitqecom.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public CustomerProfileRespDto getProfile() {

        return customerService.getProfile();
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('CUSTOMER')")
    public CustomerProfileRespDto updateProfile(
            @RequestBody CustomerUpdateDto dto) {

        return customerService.updateProfile(dto);
    }
}
