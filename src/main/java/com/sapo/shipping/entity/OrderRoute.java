package com.sapo.shipping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Table(name = "order_route")
public class OrderRoute implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "route_id")
    private int routeId;

    private String address;

    @JsonIgnore
    @JoinColumn(name = "order_id")
    @ManyToOne
    private ShippingOrder shippingOrder;

}
