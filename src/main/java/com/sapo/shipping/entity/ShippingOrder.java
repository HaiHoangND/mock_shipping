package com.sapo.shipping.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "shipping_order")
public class ShippingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_code")
    private String orderCode;
    @Column(name = "service_fee")
    private Double serviceFee;

    @JoinColumn(name = "warehouse_id")
    @ManyToOne
    private Warehouse warehouse;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Receiver receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Sender sender;

    @OneToMany(mappedBy = "shippingOrder")
    private List<OrderRoute> orderRoutes;

    @OneToMany(mappedBy = "shippingOrder")
    private List<Product> products;

    @OneToMany(mappedBy = "shippingOrder")
    private List<OrderStatus> orderStatuses;

}
