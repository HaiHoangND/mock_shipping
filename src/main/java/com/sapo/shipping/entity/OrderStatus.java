package com.sapo.shipping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "order_status")
public class OrderStatus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private ShippingOrder shippingOrder;

    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private User shipper;

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
