package com.linktic.usecase.inventory;

import static java.util.stream.Collectors.toMap;

import com.linktic.model.cart.item.CartItemModel;
import com.linktic.model.cart.item.CartModel;
import com.linktic.model.commons.BusinessException;
import com.linktic.model.commons.message.BusinessErrorMessage;
import com.linktic.model.inventory.InventoryModel;
import com.linktic.model.inventory.gateway.InventoryRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class InventoryUseCase {

  private final InventoryRepository inventoryRepository;

  public Mono<CartItemModel> verifyStock(Long productId, Long cartId, Integer quantity) {
    return inventoryRepository.getStock(productId)
        .flatMap(validateStock(cartId, quantity))
        .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUNDED)));
  }

  public Flux<CartModel> updateStock(List<CartModel> cartItems) {

    Map<Long, Integer> stockUpdatedInfo = cartItems.stream()
        .collect(toMap(CartModel::getProductId, CartModel::getQuantity));
    return inventoryRepository.addOutOfStock(stockUpdatedInfo)
        .flatMapIterable(unused -> cartItems)
        .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUNDED)));
  }

  private Function<InventoryModel, Mono<? extends CartItemModel>> validateStock(Long cartId, Integer quantity) {
    return inventoryModel -> (inventoryModel.getQuantity() <= 0 || inventoryModel.getQuantity() < quantity) ?
        Mono.error(new BusinessException(BusinessErrorMessage.NOT_ENOUGH_STOCK)) :
        Mono.just(CartItemModel.builder().cartId(cartId).product(inventoryModel).build());
  }

}
