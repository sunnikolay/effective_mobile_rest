package com.nstiazhkin.smapi.domain.account;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    
    ADMIN( "Admin" ),
    USER( "Simple user" );
    
    private final String description;
    
    Role( String description ) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String getAuthority() {
        return name();
    }
    
}
