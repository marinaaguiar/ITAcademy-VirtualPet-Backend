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
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PetType type;

    private String imageName;
    private String uniqueCharacteristic;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PetMood mood;

    private int energyLevel;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private List<PetNeeds> needs;

    public Pet(String name, PetType type, String imageName, String uniqueCharacteristic, PetMood mood, int energyLevel, List<PetNeeds> needs) {
        this.name = name;
        this.type = type;
        this.imageName = imageName;
        this.uniqueCharacteristic = uniqueCharacteristic;
        this.mood = mood;
        this.energyLevel = energyLevel;
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
}