package com.sapo.shipping.auth;

import com.sapo.shipping.auth.permission.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;
    private Role role;
    private String address;
    private String gender;
    private String phone;
    private Integer warehouseID;
    private boolean workingStatus;

}
