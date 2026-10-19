package com.cremaplace.api.repository;

import com.cremaplace.api.model.Alfajor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AlfajorRepository extends MongoRepository<Alfajor, String> {
}