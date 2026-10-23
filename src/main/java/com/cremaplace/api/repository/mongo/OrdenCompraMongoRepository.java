package com.cremaplace.api.repository.mongo;

import com.cremaplace.api.model.mongo.OrdenCompraMongo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio MongoDB para OrdenCompraMongo
 */
@Repository
public interface OrdenCompraMongoRepository extends MongoRepository<OrdenCompraMongo, String> {
    List<OrdenCompraMongo> findByProveedorId(String proveedorId);
    List<OrdenCompraMongo> findByEstado(OrdenCompraMongo.EstadoOrden estado);
}