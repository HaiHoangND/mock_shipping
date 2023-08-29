package com.sapo.shipping.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int quantity;
    private Double price;
    private String image;
    private Float weight;
    private String description;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private ShippingOrder shippingOrder;

}
