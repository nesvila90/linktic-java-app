package com.linktic.persistence.product.repository;

import com.linktic.model.commons.PageDTO;
import com.linktic.model.product.FieldName;
import com.linktic.model.product.ProductModel;
import com.linktic.model.product.ProductQueryFilters;
import com.linktic.model.product.gateway.ProductRepository;
import com.linktic.persistence.product.mapper.ProductDataEntityMapper;
import com.linktic.persistence.product.model.Product;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

  private final R2dbcEntityTemplate r2dbcEntityTemplate;

  private final ProductDataEntityMapper productDataEntityMapper;

  @Override
  public Mono<PageDTO<ProductModel>> findByFilters(ProductQueryFilters queryFilters) {

    Criteria base = createFindByFiltersCriteria(queryFilters);

    var pageNumber = queryFilters.getPageNumber();
    var pageSize = queryFilters.getPageSize();

    var pagination = PageRequest.of(pageNumber, pageSize);

    Query findByFilters = Query.query(base).with(pagination);
    return r2dbcEntityTemplate.select(Product.class)
        .matching(findByFilters)
        .all().collectList()
        .zipWith(r2dbcEntityTemplate.count(findByFilters, Product.class))
        .map(product -> new PageImpl<>(product.getT1(), pagination, product.getT2()))
        .map(productDataEntityMapper::toPageDTO);
  }

  @Override
  public Mono<ProductModel> findById(Long productId) {
    Query allProductById = Query.query(Criteria.where(FieldName.ID.name()).in(productId));
    return r2dbcEntityTemplate.select(Product.class)
        .matching(allProductById).one()
        .map(productDataEntityMapper::toModel);
  }

  private Criteria createFindByFiltersCriteria(ProductQueryFilters queryFilters) {
    var name = queryFilters.getName();
    var brand = queryFilters.getBrand();
    var initialPriceRange = queryFilters.getInitialPriceRange();
    var finalPriceRange = queryFilters.getFinalPriceRange();

    var isNameExist = validateStringEmptyAndNullValue(name);
    var isBrandExist = validateStringEmptyAndNullValue(brand);
    var isRangeExist = validateStringEmptyAndNullValue(initialPriceRange) || validateStringEmptyAndNullValue(finalPriceRange);

    var base = Criteria.empty();

    base = isNameExist ? base : base.and(FieldName.NAME.name()).is(name);
    base = isBrandExist ? base : base.and(FieldName.BRAND.name()).is(brand);
    base = isRangeExist ? base : base.and(FieldName.PRICE.name()).between(initialPriceRange, finalPriceRange);
    return base;
  }


  private boolean validateStringEmptyAndNullValue(String evaluateString) {
    return Objects.isNull(evaluateString) || evaluateString.isEmpty();
  }

}
