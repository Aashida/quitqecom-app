package com.quitqecom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//basically association entity (instead of @ManyToMany)
@Entity
@Table(name = "order_item")
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private int quantity;

    private double priceAtPurchase;

}
