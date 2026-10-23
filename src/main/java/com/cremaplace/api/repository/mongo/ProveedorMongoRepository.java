package com.cremaplace.api.repository.mongo;

import com.cremaplace.api.model.mongo.ProveedorMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio MongoDB para ProveedorMongo
 */
@Repository
public interface ProveedorMongoRepository extends MongoRepository<ProveedorMongo, String> {
}