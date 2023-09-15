package com.sapo.shipping.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MonthProfit {
    private Date date;
    private double profit;
}
