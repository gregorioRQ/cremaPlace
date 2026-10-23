package com.cremaplace.api.service;

import com.cremaplace.api.model.mongo.OrdenCompraMongo;
import com.cremaplace.api.model.mongo.RecepcionMercanciaMongo;
import com.cremaplace.api.repository.mongo.OrdenCompraMongoRepository;
import com.cremaplace.api.repository.mongo.RecepcionMercanciaMongoRepository;
import com.cremaplace.api.service.IngredientService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para registro de recepción de mercancía
 * <p>
 * INTEGRACIÓN CLAVE: Este servicio actualiza el stock de ingredientes
 * mediante el IngredientService existente (que actualmente opera en memoria)
 */
@Service
public class RecepcionService {
    private final RecepcionMercanciaMongoRepository recepcionRepository;
    private final OrdenCompraMongoRepository ordenRepository;
    private final IngredientService ingredientService;

    public RecepcionService(RecepcionMercanciaMongoRepository recepcionRepository,
                            OrdenCompraMongoRepository ordenRepository,
                            IngredientService ingredientService) {
        this.recepcionRepository = recepcionRepository;
        this.ordenRepository = ordenRepository;
        this.ingredientService = ingredientService;
    }

    /**
     * Registra una recepción de mercancía y actualiza el stock de ingredientes
     */
    public RecepcionMercanciaMongo registrarRecepcion(RecepcionMercanciaMongo recepcion) {
        // 1. Persistir la recepción en MongoDB
        RecepcionMercanciaMongo saved = recepcionRepository.save(recepcion);

        // 2. Obtener la orden asociada
        OrdenCompraMongo orden = ordenRepository.findById(recepcion.getOrdenCompraId())
                .orElseThrow(() -> new RuntimeException("Orden de compra no encontrada: " + recepcion.getOrdenCompraId()));

        // 3. Actualizar el stock de ingredientes mediante el servicio existente
        // NOTA: Este servicio actualmente usa ArrayList en memoria, así que
        // los cambios serán temporales (se perderán al reiniciar la aplicación)
        // Esto es intencional según los requisitos - abordaremos persistencia
        // de ingredientes en una fase futura
        for (RecepcionMercanciaMongo.ItemRecepcionMongo itemRec : recepcion.getItemsRecibidos()) {
            ingredientService.addStock(
                    itemRec.getIngredienteId(),
                    itemRec.getCantidad()
            );
        }

        // 4. Actualizar el estado de la orden según lo recibido
        actualizarEstadoOrden(orden.getId(), recepcion.getItemsRecibidos());

        return saved;
    }

    /**
     * Obtiene una recepción por su ID
     */
    public Optional<RecepcionMercanciaMongo> obtenerRecepcion(String id) {
        return recepcionRepository.findById(id);
    }

    /**
     * Obtiene todas las recepciones para una orden específica
     */
    public List<RecepcionMercanciaMongo> encontrarPorOrden(String ordenCompraId) {
        return recepcionRepository.findByOrdenCompraId(ordenCompraId);
    }

    /**
     * Actualiza el estado de una orden basado en los items recibidos
     */
    private void actualizarEstadoOrden(String ordenId, List<RecepcionMercanciaMongo.ItemRecepcionMongo> itemsRecibidos) {
        OrdenCompraMongo orden = ordenRepository.findById(ordenId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada: " + ordenId));

        boolean todoRecibido = true;
        boolean algoRecibido = false;

        // Verificar cada item de la orden contra lo recibido
        for (OrdenCompraMongo.ItemOrdenMongo itemOrd : orden.getItems()) {
            boolean itemCompleto = false;
            Integer recibidoParaEsteItem = 0;

            // Sumar lo recibido para este ingrediente
            for (RecepcionMercanciaMongo.ItemRecepcionMongo itemRec : itemsRecibidos) {
                if (itemRec.getIngredienteId().equals(itemOrd.getIngredienteId())) {
                    recibidoParaEsteItem += itemRec.getCantidad();
                }
            }

            // Verificar si se recibió la cantidad solicitada o más
            if (itemOrd.getCantidadSolicitada() != null && recibidoParaEsteItem >= itemOrd.getCantidadSolicitada()) {
                itemCompleto = true;
                algoRecibido = true;
            } else if (recibidoParaEsteItem > 0) {
                algoRecibido = true; // Algo recibido pero no completo
                todoRecibido = false;
            } else {
                todoRecibido = false; // Nada recibido para este item
            }

            // Actualizar la cantidad recibida en el item de la orden
            itemOrd.setCantidadRecibida(recibidoParaEsteItem);
        }

        // Determinar el nuevo estado de la orden
        if (!algoRecibido) {
            // Nada recibido
            orden.setEstado(OrdenCompraMongo.EstadoOrden.ENVIADA); // Se mantiene como enviada
        } else if (todoRecibido) {
            // Todo recibido
            orden.setEstado(OrdenCompraMongo.EstadoOrden.COMPLETADA);
        } else {
            // Parcialmente recibido
            orden.setEstado(OrdenCompraMongo.EstadoOrden.PARCIAL);
        }

        ordenRepository.save(orden);
    }
}