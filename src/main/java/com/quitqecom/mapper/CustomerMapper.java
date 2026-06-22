package com.quitqecom.mapper;

import com.quitqecom.dto.CustomerDto;
import com.quitqecom.dto.CustomerPageRespDto;
import com.quitqecom.dto.CustomerProfileRespDto;
import com.quitqecom.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerMapper {

    public CustomerProfileRespDto mapEntityToDto(
            Customer customer) {

        return new CustomerProfileRespDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getPhoneNumber(),
                customer.getAddress(),
                customer.getUser().getEmail(),
                customer.getUser().getUsername()
        );
    }

    public CustomerPageRespDto
    mapEntityToDto(Page<Customer> pages) {

        List<CustomerDto> customers =

                pages.getContent()
                        .stream()
                        .map(customer ->

                                new CustomerDto(

                                        customer.getId(),

                                        customer.getFirstName()
                                                + " "
                                                +
                                                customer.getLastName(),

                                        customer.getUser()
                                                .getEmail(),

                                        customer.getUser()
                                                .getUsername()

                                )

                        )
                        .toList();

        return new CustomerPageRespDto(

                customers,

                pages.getNumber(),

                pages.getTotalPages(),

                pages.getTotalElements(),

                pages.getSize()

        );
    }
}
