package com.itacademy.virtualpet.repository;

import com.itacademy.virtualpet.model.Pet;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PetRepository extends ReactiveMongoRepository<Pet, String> {
}
