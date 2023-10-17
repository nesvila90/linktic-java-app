package com.linktic.usecase.cart;

import com.linktic.model.cart.item.CartModel;
import com.linktic.model.cart.item.EmptyCart;
import com.linktic.model.cart.item.gateway.CartItemRepository;
import com.linktic.model.cart.session.CartSessionModel;
import com.linktic.model.cart.session.gateway.CartSessionRepository;
import com.linktic.model.commons.BusinessException;
import com.linktic.model.commons.message.BusinessErrorMessage;
import com.linktic.model.product.ProductModel;
import com.linktic.usecase.catalog.ProductCatalogUseCase;
import com.linktic.usecase.inventory.InventoryUseCase;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CartUseCase {

  private final CartItemRepository cartItemRepository;

  private final CartSessionRepository cartSessionRepository;

  private final ProductCatalogUseCase productCatalogUseCase;

  private final InventoryUseCase inventoryUseCase;

  public Mono<CartSessionModel> findById(Long cartId) {
    return cartSessionRepository.findById(cartId).switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.CART_NOT_FOUNDED)));
  }

  public Mono<CartModel> addProduct(Long productId, Long cartId, Integer quantity) {
    return cartSessionRepository.findById(cartId).switchIfEmpty(cartSessionRepository.createCartSession())
        .flatMap(cartSession -> inventoryUseCase.verifyStock(productId, cartSession.getCartId(), quantity))
        .flatMap(cartItem -> cartItemRepository.addProduct(cartItem.getCartId(), cartItem.getProduct(), quantity))
        .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.CART_ITEM_ADD_ERROR)));
  }

  public Flux<CartModel> findAllProductsByCartId(Long cartId) {
    Flux<CartModel> cartProducts = cartItemRepository.getCartProducts(cartId);
    return cartProducts.flatMap(cartModel -> Flux.defer(() -> productCatalogUseCase.findById(cartModel.getProductId()).map(addProductInfo(cartModel))));
  }

  private Function<ProductModel, CartModel> addProductInfo(CartModel cartModel) {
    return productModel1 -> {
      cartModel.setProduct(productModel1);
      return cartModel;
    };
  }

  public Mono<EmptyCart> emptyCart(Long cartId) {
    return cartItemRepository.removeProducts(cartId);
  }


}
