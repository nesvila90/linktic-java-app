package com.linktic.model.cart.item.gateway;

import com.linktic.model.cart.item.CartModel;
import com.linktic.model.cart.item.EmptyCart;
import com.linktic.model.inventory.InventoryModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CartItemRepository {

  Mono<CartModel> addProduct(Long cartId, InventoryModel product, Integer quantity);

  Flux<CartModel> getCartProducts(Long cartId);

  Mono<EmptyCart> removeProducts(Long cartId);


}
