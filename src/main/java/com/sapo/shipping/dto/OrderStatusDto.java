package com.sapo.shipping.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderStatusDto implements Serializable {
    private int id;

    private int shippingOrderId;

    private int shipperId;

    private String nextLocation;

    private int orderRouteId;

    private String status;

    private boolean isArriving;

    private LocalDateTime createdAt;
}
