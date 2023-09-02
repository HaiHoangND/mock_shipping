package com.sapo.shipping.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRouteDto {
    private int id;
    private int routeId;
    @NotBlank(message = "Address must not be empty")
    private String address;
    private int shippingOrderId;
    private int warehouseId;
}
