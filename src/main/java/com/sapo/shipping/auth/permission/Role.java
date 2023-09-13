package com.sapo.shipping.auth.permission;

import com.sapo.shipping.auth.permission.Permission;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.sapo.shipping.auth.permission.Permission.*;

@RequiredArgsConstructor
public enum Role {
    ADMIN,COORDINATOR,SHIPPER,SHOP
//    ADMIN(Collections.emptySet())
//    ,
//    COORDINATOR(Collections.emptySet()),
//    SHIPPER(Collections.emptySet()),
//    SHOP(Collections.emptySet())

//    ;
//
//    @Getter
//    private final Set<Permission> permissions;
//
//    public List<SimpleGrantedAuthority> getAuthorities() {
//        var authorities = getPermissions()
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toList());
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
//        return authorities;
//    }
}
