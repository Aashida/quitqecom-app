package com.quitqecom.service;

import com.quitqecom.dto.AdminRegisterDto;
import com.quitqecom.dto.CustomerRegisterDto;
import com.quitqecom.dto.RegisterResponseDto;
import com.quitqecom.dto.SellerRegisterDto;
import com.quitqecom.enums.Role;
import com.quitqecom.model.User;
import com.quitqecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        logger.info("Fetching user details by given username {}", username);

        User user= userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Invalid Credentials"));
        logger.info("User Details fetched for user {}", user.getUsername());
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    // CUSTOMER REGISTRATION
    public RegisterResponseDto registerCustomer(CustomerRegisterDto dto) {

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(
                new BCryptPasswordEncoder().encode(dto.password())
        );
        user.setRole(Role.CUSTOMER);
        user.setIsActive(true);

        User savedUser = userRepository.save(user);

        return new RegisterResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().toString(),
                "Customer registered successfully"
        );
    }

    // SELLER REGISTRATION
    public RegisterResponseDto registerSeller(SellerRegisterDto dto) {

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setEmail(dto.email());
        user.setPassword(
                new BCryptPasswordEncoder().encode(dto.password())
        );
        user.setRole(Role.SELLER);
        user.setIsActive(false);

        User savedUser = userRepository.save(user);

        return new RegisterResponseDto(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRole().toString(),
                "Seller registered successfully. Pending admin approval"
        );
    }

    // ADMIN CREATION
    public RegisterResponseDto createAdmin(AdminRegisterDto dto) {

        if (userRepository.findByUsername(dto.username()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(dto.email()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User admin = new User();
        admin.setUsername(dto.username());
        admin.setEmail(dto.email());
        admin.setPassword(
                new BCryptPasswordEncoder().encode(dto.password())
        );
        admin.setRole(Role.ADMIN);
        admin.setIsActive(true);

        User savedAdmin = userRepository.save(admin);

        return new RegisterResponseDto(
                savedAdmin.getId(),
                savedAdmin.getUsername(),
                savedAdmin.getEmail(),
                savedAdmin.getRole().toString(),
                "Admin created successfully"
        );
    }
}