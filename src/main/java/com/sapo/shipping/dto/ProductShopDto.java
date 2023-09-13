package com.sapo.shipping.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductShopDto {
    private int id;

    @NotBlank(message = "Name must not be empty")
    private String name;
    @NotNull(message = "Quantity must not be empty")
    private int quantity;
    @NotNull(message = "Price must not be empty")
    private Double price;
    @NotBlank(message = "Image must not be empty")
    private String image;
    @NotNull(message = "Weight must not be empty")
    private Float weight;
    private String description;
    private int shopOwnerId;
}
