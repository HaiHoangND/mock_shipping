package com.sapo.shipping.dto;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiverDto {
    private int id;

    private String name;
    private String address;
    private String phone;
}
