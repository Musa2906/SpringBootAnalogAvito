package com.avito.app.Avito.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {  //это нужно для работы с security
    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
