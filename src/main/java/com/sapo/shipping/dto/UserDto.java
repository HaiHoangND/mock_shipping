package com.sapo.shipping.dto;

import com.sapo.shipping.entity.Product;
import com.sapo.shipping.entity.ProductShop;
import com.sapo.shipping.entity.Receiver;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    private String email;
    private String fullName;
    private String role;
    private String address;
    private String phone;
    private String gender;
    private String profilePicture;
    private boolean workingStatus;
    private List<Receiver> receivers;
    private List<ProductShop> productShops;
}
