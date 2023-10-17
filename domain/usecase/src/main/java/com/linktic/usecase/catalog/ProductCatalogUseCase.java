package com.linktic.usecase.catalog;

import com.linktic.model.commons.BusinessException;
import com.linktic.model.commons.PageDTO;
import com.linktic.model.commons.message.BusinessErrorMessage;
import com.linktic.model.product.ProductModel;
import com.linktic.model.product.ProductQueryFilters;
import com.linktic.model.product.gateway.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductCatalogUseCase {

  private final ProductRepository productRepository;

  public Mono<PageDTO<ProductModel>> findByFilters(ProductQueryFilters filters) {
    return productRepository.findByFilters(filters)
        .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUNDED)));
  }

  public Mono<ProductModel> findById(Long productId) {
    return productRepository.findById(productId)
        .switchIfEmpty(Mono.error(new BusinessException(BusinessErrorMessage.PRODUCT_NOT_FOUNDED)));
  }


}
