package com.quitqecom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String shopName;

    private String businessAddress;

    private String gstNumber;

    @OneToOne
    private User user;


}
