package com.sapo.shipping.entity;

import jakarta.persistence.*;
import lombok.Data;

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
}
