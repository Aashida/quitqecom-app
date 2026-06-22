package com.quitqecom.controller;

import com.quitqecom.dto.AdminDashboardDto;
import com.quitqecom.dto.AdminProductPageRespDto;
import com.quitqecom.dto.CustomerPageRespDto;
import com.quitqecom.dto.OrderPageRespDto;
import com.quitqecom.model.*;
import com.quitqecom.repository.CategoryRepository;
import com.quitqecom.repository.SellerRepository;
import com.quitqecom.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final AdminService adminService;
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;

    @PutMapping("/approve-seller/{sellerId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String approveSeller(
            @PathVariable int sellerId) {

        return adminService.approveSeller(sellerId);
    }

    @GetMapping("/dashboard")
    public AdminDashboardDto dashboard() {

        return adminService.dashboard();
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {

        return adminService.getAllUsers();
    }

    @GetMapping("/customers")
    @PreAuthorize("hasRole('ADMIN')")
    public CustomerPageRespDto
    getAllCustomers(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) String search
    ) {

        return adminService
                .getAllCustomers(page, size, search);
    }

    @GetMapping("/sellers")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Seller> getAllSellers() {

        return adminService.getAllSellers();
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminProductPageRespDto getAllProducts(

            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "5")
            int size,
            @RequestParam(required = false)
            String search,
            @RequestParam(required = false)
            String category,
            @RequestParam(required = false)
            String seller,
            @RequestParam(required = false)
            Boolean inStock,
            @RequestParam(defaultValue = "asc")
            String direction

    ) {

        return adminService
                .getAllProducts(

                        page,

                        size,

                        search,

                        category,

                        seller,

                        inStock,

                        direction

                );
    }
    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public OrderPageRespDto getAllOrders(

            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(required = false) String status
    ) {

        return adminService.getAllOrders(page, size, sortBy, direction, status);
    }

    @GetMapping("/categories")
    public List<String> getCategories() {

        return categoryRepository
                .findAll()
                .stream()
                .map(Category::getCategoryName)
                .toList();
    }

    @GetMapping("/shops")
    public List<String> getShops() {

        return sellerRepository
                .findAll()
                .stream()
                .map(Seller::getShopName)
                .toList();
    }
}