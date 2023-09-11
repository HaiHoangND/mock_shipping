package com.sapo.shipping.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sapo.shipping.auth.permission.Role;
import com.sapo.shipping.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    @JsonProperty("access_token")
    private String accessToken;
    private String userName;
    private String profilePicture;
    private Role role;
    private Integer id;
}
