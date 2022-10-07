package com.example.vk.Entity;

public enum Permission {

    USER("USER"), ADMIN("ADMIN");

    final String permission;

    Permission(String permission) {
        this.permission = permission;
    }
}
