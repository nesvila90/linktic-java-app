package com.linktic.persistence.cart.session.repository;

import com.linktic.model.cart.session.CartSessionModel;
import com.linktic.model.cart.session.gateway.CartSessionRepository;
import com.linktic.persistence.cart.session.mapper.CartSessionMapper;
import com.linktic.persistence.cart.session.model.CartSession;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartSessionRepositoryImpl implements CartSessionRepository {

  private final R2dbcEntityTemplate r2dbcEntityTemplate;

  private final CartSessionMapper cartSessionMapper;

  @Override
  public Mono<CartSessionModel> findById(Long cartId) {
    Query cartSessionQuery = Query.query(Criteria.where("cartId").is(cartId));
    return r2dbcEntityTemplate.selectOne(cartSessionQuery, CartSession.class)
        .flatMap(cartSession -> Mono.just(cartSessionMapper.toModel(cartSession)));
  }

  @Override
  public Mono<CartSessionModel> createCartSession() {
    return r2dbcEntityTemplate.insert(CartSession.class).using(createDefaultCartSession())
        .flatMap(cartSession -> Mono.just(cartSessionMapper.toModel(cartSession)));
  }

  @Override
  public Mono<Integer> updateCartSession(String user, Double total, Long cartId) {
    return r2dbcEntityTemplate.update(CartSession.class)
        .matching(Query.query(Criteria.where("cartId").is(cartId)))
        .apply(Update.update("user", user).set("total", total));
  }

  private CartSession createDefaultCartSession() {
    return CartSession.builder()
        .user("ANONYMOUS")
        .total(0D)
        .createdAt(LocalDateTime.now())
        .build();
  }
}
