package com.linktic.persistence.inventory;

import com.linktic.model.inventory.InventoryModel;
import com.linktic.model.inventory.gateway.InventoryRepository;
import com.linktic.model.product.FieldName;
import com.linktic.persistence.product.model.Product;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class InventoryRepositoryImpl implements InventoryRepository {

  private final R2dbcEntityTemplate r2dbcEntityTemplate;

  @Override
  public Mono<InventoryModel> getStock(Long productId) {
    Criteria verifyStockCriteria = buildVerifyStockCriteria(productId);
    Query isStockAvailableQuery = Query.query(verifyStockCriteria);
    return r2dbcEntityTemplate.select(Product.class)
        .matching(isStockAvailableQuery)
        .one().map(product -> InventoryModel.builder().productId(productId).quantity(product.getStockQuantity()).build());
  }


  @Override
  public Mono<Void> addOutOfStock(Map<Long, Integer> updatedStockInfo) {
    return Mono.just(updatedStockInfo.entrySet().stream().map(stock -> {
      Update stockQuantityUpdate = Update.update("stockQuantity", -stock.getValue());
      Query findProductsByIdQuery = Query.query(Criteria.where("productId").is(stock.getKey()));
      return r2dbcEntityTemplate.update(findProductsByIdQuery, stockQuantityUpdate, Product.class);
    })).then();
  }

  private Criteria buildVerifyStockCriteria(Long productId) {
    return Criteria.where(FieldName.ID.name()).is(productId)
        .and(FieldName.STOCK_QUANTITY.name())
        .greaterThan(0);
  }


}
