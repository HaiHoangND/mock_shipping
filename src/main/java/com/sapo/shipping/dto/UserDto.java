package com.sapo.shipping.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    private String email;
    private String password;
    private String fullName;
    private String role;
    private int warehouseId;
    private String address;
    private String phone;
    private String gender;
    private String profilePicture;
    private boolean workingStatus;
}
