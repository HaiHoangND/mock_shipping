package com.sapo.shipping.entity;

import jakarta.persistence.*;
import lombok.Data;

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

//    @JoinColumn(name = "order_id")
//    @OneToMany(mappedBy = "order")
//    private Order order;
}
