package com.linktic.api;

import com.linktic.api.request.ProductCartDTO;
import com.linktic.api.util.ResponseUtil;
import com.linktic.model.commons.TechnicalException;
import com.linktic.model.commons.message.TechnicalErrorMessage;
import com.linktic.usecase.cart.CartUseCase;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartHandler {

  private final CartUseCase cartUseCase;

  private final ResponseUtil responseUtil;

  public Mono<ServerResponse> addProduct(ServerRequest serverRequest) {
    return serverRequest.bodyToMono(ProductCartDTO.class)
        .flatMap(productCartDTO -> cartUseCase.addProduct(productCartDTO.getProductId(), productCartDTO.getCartId(), productCartDTO.getQuantity()))
        .single()
        .flatMap(responseUtil::buildResponse);
  }

  public Mono<ServerResponse> getCartContent(ServerRequest serverRequest) {
    Long cartId = extractCartIdParameter(serverRequest);
    return cartUseCase.findAllProductsByCartId(cartId)
        .collectList()
        .flatMap(responseUtil::buildCartContentResponse)
        .single();
  }

  public Mono<ServerResponse> removeAllCartContent(ServerRequest serverRequest) {
    Long id = Long.valueOf(serverRequest.pathVariable("id"));
    return cartUseCase.emptyCart(id)
        .flatMap(responseUtil::buildCartContentResponse)
        .single();
  }


  @SneakyThrows
  private Long extractCartIdParameter(ServerRequest serverRequest) {
    return serverRequest.queryParam("cartId")
        .map(Long::valueOf)
        .orElseThrow(() -> new TechnicalException(TechnicalErrorMessage.BAD_REQUEST));
  }


}
