package com.sapo.shipping.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseDto {
    private int id;
    private String name;
    private String address;
}
