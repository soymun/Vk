package com.example.vk.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role{
    USER(Set.of(Permission.USER)), ADMIN(Set.of(Permission.USER, Permission.ADMIN));
    final Set<Permission> text;

    Role(Set<Permission> text) {
        this.text = text;
    }

    public Set<SimpleGrantedAuthority> getAuthority() {
        return text.stream().map(role -> new SimpleGrantedAuthority(role.permission)).collect(Collectors.toSet());
    }
}
