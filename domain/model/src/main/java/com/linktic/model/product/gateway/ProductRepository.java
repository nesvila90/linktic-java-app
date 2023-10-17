package com.linktic.model.product.gateway;

import com.linktic.model.commons.PageDTO;
import com.linktic.model.product.ProductModel;
import com.linktic.model.product.ProductQueryFilters;
import reactor.core.publisher.Mono;

public interface ProductRepository {

  Mono<PageDTO<ProductModel>> findByFilters(ProductQueryFilters queryFilters);

  Mono<ProductModel> findById(Long productId);
}
