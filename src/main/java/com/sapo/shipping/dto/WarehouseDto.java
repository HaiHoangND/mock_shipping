package com.sapo.shipping.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WarehouseDto {
    private int id;
    @NotBlank(message = "must not be empty")
    private String name;
    @NotBlank(message = "must not be empty")
    private String address;
}
