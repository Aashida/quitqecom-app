package com.quitqecom.service;

import com.quitqecom.dto.AdminDashboardDto;
import com.quitqecom.dto.AdminProductPageRespDto;
import com.quitqecom.dto.CustomerPageRespDto;
import com.quitqecom.dto.OrderPageRespDto;
import com.quitqecom.enums.OrderStatus;
import com.quitqecom.mapper.AdminProductMapper;
import com.quitqecom.mapper.CustomerMapper;
import com.quitqecom.mapper.OrderMapper;
import com.quitqecom.model.Customer;
import com.quitqecom.model.Order;
import com.quitqecom.model.Product;
import com.quitqecom.model.Seller;
import com.quitqecom.model.User;
import com.quitqecom.repository.CustomerRepository;
import com.quitqecom.repository.OrderRepository;
import com.quitqecom.repository.ProductRepository;
import com.quitqecom.repository.SellerRepository;
import com.quitqecom.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    public final OrderMapper orderMapper;
    private final CustomerMapper customerMapper;
    private final AdminProductMapper adminProductMapper;


    public String approveSeller(int sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        User user = seller.getUser();
        user.setIsActive(true);
        userRepository.save(user);
        return "Seller approved successfully";
    }

    public AdminDashboardDto dashboard() {

        return new AdminDashboardDto(
                userRepository.count(),
                customerRepository.count(),
                sellerRepository.count(),
                productRepository.count(),
                orderRepository.count()
        );
    }

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public CustomerPageRespDto
    getAllCustomers(
            int page,
            int size,
            String search
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        Page<Customer> pages;

        if(search != null &&
                !search.isBlank()) {

            pages =
                    customerRepository
                            .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrUser_EmailContainingIgnoreCaseOrUser_UsernameContainingIgnoreCase(

                                    search,

                                    search,

                                    search,

                                    search,

                                    pageable
                            );

        } else {

            pages =
                    customerRepository
                            .findAll(pageable);
        }

        return customerMapper
                .mapEntityToDto(
                        pages
                );
    }

    public List<Seller> getAllSellers() {

        return sellerRepository.findAll();
    }

    public AdminProductPageRespDto
    getAllProducts(

            int page,

            int size,

            String search,

            String category,

            String seller,

            Boolean inStock,

            String direction

    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), "price"));

        Page<Product> pages =

                productRepository
                        .searchProducts(

                                search,

                                category,

                                seller,

                                inStock,

                                pageable

                        );

        return adminProductMapper
                .mapEntityToDto(
                        pages
                );
    }
    public OrderPageRespDto getAllOrders(
            int page,
            int size,
            String sortBy,
            String direction,
            String status
    ) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                Sort.Direction.fromString(
                                        direction
                                ),
                                sortBy
                        )
                );

        Page<Order> pages;

        if(status != null &&
                !status.isBlank()) {

            pages =
                    orderRepository.findByStatus(
                            OrderStatus.valueOf(
                                    status
                            ),
                            pageable
                    );

        } else {

            pages =
                    orderRepository.findAll(
                            pageable
                    );
        }

        return orderMapper
                .mapEntityToDto(
                        pages
                );
    }


}
