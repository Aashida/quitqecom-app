// SecurityConfig.java
package com.quitqecom.config;

import com.quitqecom.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final JwtFilter jwtFilter;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // PUBLIC APIs
                        .requestMatchers(HttpMethod.POST, "/api/register/customer").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/register/seller").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/product/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product/all/v2").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product/get-one/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product/price-range").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product/search").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/product/category/stat").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/category/all").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/category/*").permitAll()

                        // AUTH APIs
                        .requestMatchers(HttpMethod.GET, "/api/auth/login").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/auth/user-details").authenticated()

                        // ADMIN APIs
                        .requestMatchers(HttpMethod.POST, "/api/register/admin").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/api/admin/approve-seller/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/admin/dashboard").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/admin/users").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/admin/customers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/admin/sellers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/admin/products").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/admin/orders").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/payment/refund/*").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/category/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/category/update/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/category/delete/*").hasRole("ADMIN")

                        // CUSTOMER APIs
                        .requestMatchers(HttpMethod.GET, "/api/customer/profile").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/api/customer/profile").hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.POST, "/api/cart/add/*").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/cart").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/api/cart/update/*").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/api/cart/remove/*").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/api/cart/clear").hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.POST, "/api/order/place").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/order/my-orders").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/order/*").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.PUT, "/api/order/cancel/*").hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.POST, "/api/payment/pay/*").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/api/payment/*").hasRole("CUSTOMER")

                        .requestMatchers(HttpMethod.POST, "/api/shipping/create/*").hasRole("CUSTOMER")

                        // SELLER APIs
                        .requestMatchers(HttpMethod.GET, "/api/seller/profile").hasRole("SELLER")
                        .requestMatchers(HttpMethod.PUT, "/api/seller/profile").hasRole("SELLER")

                        .requestMatchers(HttpMethod.GET, "/api/seller/products").hasRole("SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/seller/products/*").hasRole("SELLER")

                        .requestMatchers(HttpMethod.GET, "/api/seller/dashboard").hasRole("SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/seller/orders").hasRole("SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/seller/revenue").hasRole("SELLER")

                        .requestMatchers(HttpMethod.POST, "/api/product/add").hasRole("SELLER")
                        .requestMatchers(HttpMethod.POST, "/api/product/addV2").hasRole("SELLER")
                        .requestMatchers(HttpMethod.PUT, "/api/product/update/*").hasRole("SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/api/product/delete/*").hasRole("SELLER")

                        // SELLER OR ADMIN APIs
                        .requestMatchers(HttpMethod.PUT, "/api/shipping/status/*")
                        .hasAnyRole("SELLER", "ADMIN")

                        // AUTHENTICATED USERS
                        .requestMatchers(HttpMethod.GET, "/api/shipping/*")
                        .authenticated()

                        // ORDER ITEM APIs
                        .requestMatchers(HttpMethod.POST, "/api/order/item/add/*/*")
                        .hasAnyRole("ADMIN", "SELLER")

                        .requestMatchers(HttpMethod.GET, "/api/order/item/by-order/*")
                        .hasAnyRole("ADMIN", "SELLER")

                        .requestMatchers(HttpMethod.GET, "/api/order/item/details/*")
                        .hasAnyRole("ADMIN", "SELLER")

                        .requestMatchers(HttpMethod.GET, "/api/order/item/by-product/*")
                        .hasAnyRole("ADMIN", "SELLER")

                        //Review APIs
                        .requestMatchers(HttpMethod.GET, "/api/review/product/*").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/review/rating/*").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/review/add/*").hasRole("CUSTOMER")

                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class)

                .httpBasic(Customizer.withDefaults());
        

        return http.build();
    }
}