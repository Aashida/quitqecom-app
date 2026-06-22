package com.quitqecom.repository;

import com.quitqecom.model.Customer;
import com.quitqecom.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional<Customer> findByUser(User user);

    Page<Customer>
    findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrUser_EmailContainingIgnoreCaseOrUser_UsernameContainingIgnoreCase(

            String firstName,

            String lastName,

            String email,

            String username,

            Pageable pageable
    );
}
