package com.sapo.shipping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shopOwner_id")
    private User shopOwner;

}
