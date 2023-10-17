package com.linktic.usecase.checkout;

import com.linktic.model.cart.item.CartModel;
import com.linktic.model.commons.BusinessException;
import com.linktic.model.commons.message.BusinessErrorMessage;
import com.linktic.usecase.cart.CartUseCase;
import com.linktic.usecase.inventory.InventoryUseCase;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CheckoutUseCase {

  private final InventoryUseCase inventoryUseCase;

  private final CartUseCase cartUseCase;

  @SneakyThrows
  public Flux<CartModel> buy(Long cartId) {
    return cartUseCase.findAllProductsByCartId(cartId).collectList()
        .flatMapMany(cartModels -> {
          inventoryUseCase.updateStock(cartModels);
          return Flux.fromIterable(cartModels);
        }).switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.CHECKOUT_ERROR)));


  }

}
