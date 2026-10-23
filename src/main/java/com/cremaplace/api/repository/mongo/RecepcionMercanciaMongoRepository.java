package com.cremaplace.api.repository.mongo;

import com.cremaplace.api.model.mongo.RecepcionMercanciaMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio MongoDB para RecepcionMercanciaMongo
 */
@Repository
public interface RecepcionMercanciaMongoRepository extends MongoRepository<RecepcionMercanciaMongo, String> {
    List<RecepcionMercanciaMongo> findByOrdenCompraId(String ordenCompraId);
}