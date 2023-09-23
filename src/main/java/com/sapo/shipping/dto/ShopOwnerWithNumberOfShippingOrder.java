package com.sapo.shipping.dto;

import com.sapo.shipping.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShopOwnerWithNumberOfShippingOrder {
    User shopOwner;
    int numberOfShippingOrders;

    public ShopOwnerWithNumberOfShippingOrder(User shopOwner) {
        this.shopOwner = shopOwner;
    }
}
