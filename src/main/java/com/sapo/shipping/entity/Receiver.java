package com.sapo.shipping.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table
public class Receiver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;
    private String phone;

    @OneToMany(mappedBy = "receiver")
    private List<ShippingOrder> shippingOrders;
}
