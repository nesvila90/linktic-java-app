package com.linktic.api;

import static com.linktic.api.util.RequestUtil.extractFilters;

import com.linktic.api.mapper.ProductMapper;
import com.linktic.api.util.ResponseUtil;
import com.linktic.usecase.catalog.ProductCatalogUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductCatalogHandler {

  private final ProductCatalogUseCase productCatalogUseCase;

  private final ProductMapper productMapper;

  private final ResponseUtil responseUtil;

  public Mono<ServerResponse> finder(ServerRequest serverRequest) {
    return extractFilters(serverRequest)
        .map(productMapper::toProductQueryFilters)
        .flatMap(productCatalogUseCase::findByFilters)
        .flatMap(responseUtil::buildResponse);
  }


}
