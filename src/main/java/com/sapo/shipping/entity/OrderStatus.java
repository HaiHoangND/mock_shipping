package com.sapo.shipping.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private ShippingOrder shippingOrder;

    @OneToOne
    @JoinColumn(name = "shipper_id")
    private User user;


    @Column(name = "next_location")
    private String nextLocation;

    @JoinColumn(name = "current_location_route_id")
    @ManyToOne
    private OrderRoute orderRoute;

    private String status;

    @Column(name = "is_arriving")
    private boolean isArriving;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;


}
