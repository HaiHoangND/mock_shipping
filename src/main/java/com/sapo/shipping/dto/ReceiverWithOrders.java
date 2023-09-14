package com.sapo.shipping.dto;

import com.sapo.shipping.entity.Receiver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReceiverWithOrders {
    private Receiver receiver;
    private int numberOfOrders;
    private int successfulOrders;

    public ReceiverWithOrders(Receiver receiver) {
        this.receiver = receiver;
    }
}
