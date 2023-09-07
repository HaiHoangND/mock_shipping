package com.sapo.shipping.auth.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    COORDINATOR_READ("coordinator:read"),
    COORDINATOR_UPDATE("coordinator:update"),
    COORDINATOR_CREATE("coordinator:create"),
    COORDINATOR_DELETE("coordinator:delete"),
    SHIPPER_READ("shipper:read"),
    SHIPPER_UPDATE("shipper:update"),
    SHIPPER_CREATE("shipper:create"),
    SHIPPER_DELETE("shipper:delete")

    ;


    @Getter
    private final String permission;
}
