package com.sapo.shipping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int quantity;
    private Double price;
    private String image;
    private Float weight;
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private ShippingOrder shippingOrder;

}
