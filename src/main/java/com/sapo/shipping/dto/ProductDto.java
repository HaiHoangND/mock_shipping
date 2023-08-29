package com.sapo.shipping.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {
    private int id;

    private String name;
    private int quantity;
    private Double price;
    private String image;
    private Float weight;
    private String description;


    private int shippingOrderId;
}
