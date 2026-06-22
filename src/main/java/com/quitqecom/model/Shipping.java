package com.quitqecom.model;

import com.quitqecom.enums.ShippingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Shipping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String receiverName;

    private String phoneNumber;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String pincode;

    private String country;

    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus;

    private String trackingNumber;

    @OneToOne
    private Order order;
}