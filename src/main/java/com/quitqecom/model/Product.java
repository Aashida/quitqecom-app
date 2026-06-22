package com.quitqecom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String productName;

    @Column(length = 1000)
    private String description;

    private double price;

    private int stock;

    private Integer offerPercentage = 0;

    private String imagePath;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Seller seller;

}
