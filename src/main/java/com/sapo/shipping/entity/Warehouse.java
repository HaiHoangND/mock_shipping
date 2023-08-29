package com.sapo.shipping.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table
public class Warehouse implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;

//    @JoinColumn(name = "shipping_order_id")
    @OneToMany(mappedBy = "warehouse")
    private List<ShippingOrder> shippingOrders;


}
