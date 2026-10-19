package com.cremaplace.api.repository;

import com.cremaplace.api.model.Tarta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TartaRepository extends MongoRepository<Tarta, String> {
}