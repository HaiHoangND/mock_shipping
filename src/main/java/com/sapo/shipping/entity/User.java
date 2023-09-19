package com.sapo.shipping.entity;


import com.sapo.shipping.auth.permission.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;

@Entity
@Data
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;
    private String password;
    @Column(name = "full_name")
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String address;
    private String phone;
    private String gender;
    @Column(name = "profile_picture")
    private String profilePicture;
    @Column(name = "working_status")
    private boolean workingStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       Set<GrantedAuthority> authorities = new HashSet<>();
       authorities.add(new SimpleGrantedAuthority("ROLE_"+this.getRole().name()));
    return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
