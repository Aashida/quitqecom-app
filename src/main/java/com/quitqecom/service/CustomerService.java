package com.quitqecom.service;

import com.quitqecom.dto.CustomerProfileRespDto;
import com.quitqecom.dto.CustomerRegisterDto;
import com.quitqecom.dto.CustomerUpdateDto;
import com.quitqecom.mapper.CustomerMapper;
import com.quitqecom.model.Customer;
import com.quitqecom.model.User;
import com.quitqecom.repository.CustomerRepository;
import com.quitqecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CustomerMapper customerMapper;

    public void createCustomer(CustomerRegisterDto dto, User user) {

        Customer customer = new Customer();

        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setPhoneNumber(dto.phoneNumber());
        customer.setAddress(dto.address());
        customer.setUser(user);

        customerRepository.save(customer);
    }

    public Customer getById(int id){

        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer Id Invalid"));
    }

    private Customer getLoggedInCustomer() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"));

        return customerRepository
                .findByUser(user)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Customer not found"));
    }

    public CustomerProfileRespDto getProfile() {

        Customer customer =
                getLoggedInCustomer();

        return customerMapper
                .mapEntityToDto(customer);
    }

    public CustomerProfileRespDto updateProfile(
            CustomerUpdateDto dto) {

        Customer customer =
                getLoggedInCustomer();

        customer.setFirstName(dto.firstName());
        customer.setLastName(dto.lastName());
        customer.setPhoneNumber(dto.phoneNumber());
        customer.setAddress(dto.address());

        customerRepository.save(customer);

        return customerMapper
                .mapEntityToDto(customer);
    }
}