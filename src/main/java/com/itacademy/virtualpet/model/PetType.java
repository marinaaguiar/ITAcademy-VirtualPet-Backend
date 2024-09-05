package com.itacademy.virtualpet.model;

public enum PetType {
    ORANGE_CAT("Orange Cat"),
    BLACK_CAT("Black Cat"),
    LIGHT_GRAY_CAT("Light Gray Cat"),
    DARK_GRAY_CAT("Dark Gray Cat");

    private final String displayName;

    PetType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}