package com.sapo.shipping.dto;

import com.sapo.shipping.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserWithStatus {
    private User user;
    private String status;
    public UserWithStatus(User user) {
        this.user = user;
    }
}
