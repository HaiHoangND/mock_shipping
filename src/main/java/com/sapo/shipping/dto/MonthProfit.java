package com.sapo.shipping.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonthProfit {
    private int date;
    private double profit;
}
