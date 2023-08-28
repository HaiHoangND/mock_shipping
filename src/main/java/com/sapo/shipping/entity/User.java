package com.sapo.shipping.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    @Column(name = "full_name")
    private String fullName;
    private String role;
    @Column(name = "warehouse_id")
    private int warehouseId;
    private String address;
    private String phone;
    private String gender;
    @Column(name = "profile_picture")
    private String profilePicture;
    @Column(name = "working_status")
    private boolean workingStatus;
}
