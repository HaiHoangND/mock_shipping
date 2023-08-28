package com.sapo.shipping.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table
public class Sender {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String address;
    private String phone;
    @OneToMany(mappedBy = "sender")
    private List<ShippingOrder> shippingOrders;
}
