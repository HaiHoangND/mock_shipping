package com.sapo.shipping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "product_shop")
public class ProductShop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int quantity;
    private Double price;
    private String image;
    private Float weight;
    private String description;

    @Column(name = "product_code")
    private String productCode;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop_owner_id")
    private User shopOwner;

}
