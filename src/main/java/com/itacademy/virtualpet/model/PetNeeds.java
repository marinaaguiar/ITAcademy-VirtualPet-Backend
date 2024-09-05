package com.itacademy.virtualpet.model;

public enum PetNeeds {
    FULL("Full"),
    HYDRATED("Hydrated"),
    LOVED("Loved"),
    HUNGRY("Hungry"),
    THIRSTY("Thirsty"),
    CARE("Care");

    private final String displayName;

    PetNeeds(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
