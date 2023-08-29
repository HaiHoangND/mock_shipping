package com.sapo.shipping.dto;


import com.sapo.shipping.entity.OrderRoute;
import com.sapo.shipping.entity.OrderStatus;
import com.sapo.shipping.entity.Product;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShippingOrderDto implements Serializable {
    private int id;

    @NotBlank(message = "Code must not be empty")
    private String orderCode;
    private Double serviceFee;

    private int warehouseId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int receiverId;

    private int senderId;

    private List<OrderRoute> orderRoutes;

    private List<Product> products;

    private List<OrderStatus> orderStatuses;
}
