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
    ADMIN, COORDINATOR, SHIPPER, SHOP
//    USER(Collections.emptySet()),
//    ADMIN(
//            Set.of(
//                    ADMIN_READ,
//                    ADMIN_UPDATE,
//                    ADMIN_DELETE,
//                    ADMIN_CREATE,
//                    COORDINATOR_READ,
//                    COORDINATOR_UPDATE,
//                    COORDINATOR_DELETE,
//                    COORDINATOR_CREATE,
//                    SHIPPER_READ,
//                    SHIPPER_UPDATE,
//                    SHIPPER_DELETE,
//                    SHIPPER_CREATE
//            )
//    ),
//    COORDINATOR(
//            Set.of(
//                    COORDINATOR_READ,
//                    COORDINATOR_UPDATE,
//                    COORDINATOR_DELETE,
//                    COORDINATOR_CREATE
//            )
//    ),
//    SHIPPER(
//            Set.of(
//                    SHIPPER_READ,
//                    SHIPPER_UPDATE,
//                    SHIPPER_DELETE,
//                    SHIPPER_CREATE
//                    )
//    )
//
//
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
