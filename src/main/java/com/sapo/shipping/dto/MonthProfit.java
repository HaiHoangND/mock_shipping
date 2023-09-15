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
    private String dateStr;

    public MonthProfit(Date date, double profit) {
        this.date = date;
        this.profit = profit;
    }
}
