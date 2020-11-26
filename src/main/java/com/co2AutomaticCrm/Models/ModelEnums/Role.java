package com.co2AutomaticCrm.Models.ModelEnums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    MANAGER, ADMIN, STOREKEEPER, GUN_MASTER;

    @Override
    public String getAuthority() {
        return name();
    }
}
