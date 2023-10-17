package com.linktic.persistence.cart.item.repository;

import com.linktic.model.cart.item.CartModel;
import com.linktic.model.cart.item.EmptyCart;
import com.linktic.model.cart.item.gateway.CartItemRepository;
import com.linktic.model.commons.BusinessException;
import com.linktic.model.commons.message.BusinessErrorMessage;
import com.linktic.model.inventory.InventoryModel;
import com.linktic.persistence.cart.item.mapper.CartMapper;
import com.linktic.persistence.cart.item.model.CartItem;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartItemRepositoryImpl implements CartItemRepository {

  private static final String QUANTITY_COLUMN = "quantity";
  private static final String CART_ID_COLUMN = "cartId";
  private static final String PRODUCT_ID_COLUMN = "productId";

  private final R2dbcEntityTemplate r2dbcEntityTemplate;

  private final CartMapper cartMapper;

  @Override
  public Mono<CartModel> addProduct(Long cartId, InventoryModel product, Integer quantity) {
    Query findCartItemQuery = buildFindCartItemQuery(cartId, product);
    return r2dbcEntityTemplate.selectOne(findCartItemQuery, CartItem.class)
        .flatMap(exist -> updateCartItem(quantity, exist))
        .switchIfEmpty(createNewCartItem(cartId, product, quantity))
        .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.CART_NOT_FOUNDED)));
  }

  @Override
  public Flux<CartModel> getCartProducts(Long cartId) {
    return r2dbcEntityTemplate.select(Query.query(Criteria.where(CART_ID_COLUMN)
            .is(cartId)), CartItem.class)
        .flatMap(this::mapToCartItemsModel);
  }

  @Override
  public Mono<EmptyCart> removeProducts(Long cartId) {
    Query cartQuery = Query.query(Criteria.where(CART_ID_COLUMN).is(cartId));
    return r2dbcEntityTemplate.delete(CartItem.class).matching(cartQuery).all()
        .thenReturn(new EmptyCart());
  }

  private Mono<CartModel> createNewCartItem(Long cartId, InventoryModel product, Integer quantity) {
    return r2dbcEntityTemplate.insert(CartItem.class)
        .using(getNewCartItem(cartId, product, quantity))
        .map(this::mapToCartModel);
  }

  private Mono<CartModel> updateCartItem(Integer quantity, CartItem exist) {
    exist.setQuantity(quantity);
    return r2dbcEntityTemplate.update(CartItem.class)
        .apply(Update.update(QUANTITY_COLUMN, quantity))
        .thenReturn(mapToCartModel(exist));
  }

  private Query buildFindCartItemQuery(Long cartId, InventoryModel product) {
    return Query.query(Criteria.where(CART_ID_COLUMN).is(cartId)
        .and(PRODUCT_ID_COLUMN).is(product.getProductId()));
  }

  private Flux<CartModel> mapToCartItemsModel(CartItem cartItem) {
    return Flux.just(cartMapper.toModel(cartItem));
  }

  private CartModel mapToCartModel(CartItem cartItem) {
    return cartMapper.toModel(cartItem);
  }

  private CartItem getNewCartItem(Long cartId, InventoryModel product, Integer quantity) {
    return CartItem.builder()
        .cartId(cartId)
        .productId(product.getProductId())
        .quantity(quantity)
        .cartItemAddedDate(LocalDateTime.now())
        .build();
  }


}
