package com.example.vk.Entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER("USER");
    final String text;

    Role(String text) {
        this.text = text;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
