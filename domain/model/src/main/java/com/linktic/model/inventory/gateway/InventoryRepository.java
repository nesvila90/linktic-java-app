package com.linktic.model.inventory.gateway;

import com.linktic.model.inventory.InventoryModel;
import java.util.Map;
import reactor.core.publisher.Mono;

public interface InventoryRepository {

  Mono<InventoryModel> getStock(Long productId);

  Mono<Void> addOutOfStock(Map<Long, Integer> updatedStockInfo);
}
