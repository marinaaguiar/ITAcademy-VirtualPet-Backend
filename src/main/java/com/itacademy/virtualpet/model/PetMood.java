package com.itacademy.virtualpet.model;

public enum PetMood {
    HAPPY("Happy"),
    SAD("Sad"),
    EXCITED("Excited"),
    TIRED("Tired");

    private final String displayName;

    PetMood(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
