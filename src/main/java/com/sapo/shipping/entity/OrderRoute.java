package com.sapo.shipping.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_route")
public class OrderRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "route_id")
    private int routeId;

    private String address;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private ShippingOrder shippingOrder;
}
