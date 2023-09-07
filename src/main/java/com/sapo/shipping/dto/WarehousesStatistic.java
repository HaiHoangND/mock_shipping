package com.sapo.shipping.dto;

import com.sapo.shipping.entity.Warehouse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WarehousesStatistic {
    private Warehouse warehouse;
    private Long delivering;
    private int shippers;
    private int shippingOrders;

    public WarehousesStatistic(Warehouse warehouse) {
        this.warehouse = warehouse;
    }
}
