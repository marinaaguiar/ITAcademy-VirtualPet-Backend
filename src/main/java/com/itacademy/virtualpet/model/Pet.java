package com.itacademy.virtualpet.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "pets")
public class Pet {

    @Id
    private String id = UUID.randomUUID().toString();
    private String userId;
    private String name;
    private String uniqueCharacteristic;
    private int energyLevel;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PetType type;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PetMood mood;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<PetNeeds> needs;

    public Pet(String name, PetType type, String uniqueCharacteristic, PetMood mood, int energyLevel, List<PetNeeds> needs) {
        this.name = name;
        this.uniqueCharacteristic = uniqueCharacteristic;
        this.energyLevel = energyLevel;
        this.type = type;
        this.mood = mood;
        this.needs = needs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getUniqueCharacteristic() {
        return uniqueCharacteristic;
    }

    public void setUniqueCharacteristic(String uniqueCharacteristic) {
        this.uniqueCharacteristic = uniqueCharacteristic;
    }

    public int getEnergyLevel() {
        return energyLevel;
    }

    public void setEnergyLevel(int energyLevel) {
        this.energyLevel = energyLevel;
    }

    public PetMood getMood() {
        return mood;
    }

    public void setMood(PetMood mood) {
        this.mood = mood;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<PetNeeds> getNeeds() {
        return needs;
    }

    public String getUserId() {
        return userId;
    }

    public void setNeeds(List<PetNeeds> needs) {
        this.needs = needs;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", uniqueCharacteristic='" + uniqueCharacteristic + '\'' +
                ", energyLevel=" + energyLevel +
                ", type=" + type +
                ", mood=" + mood +
                ", needs=" + needs +
                '}';
    }
}
