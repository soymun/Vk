package com.example.vk.Entity;

public enum TypeDialog {

    SOLO("SOLO"), DUO("DUO"), GROUP("GROUP");

    final String p;

    TypeDialog(String p) {
        this.p = p;
    }

    public String getP() {
        return p;
    }
}
