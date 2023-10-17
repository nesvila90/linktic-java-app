package com.linktic.api;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.linktic.api.commons.CartProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class CartRouter {

  private final CartProperties cartProperties;

  @Bean
  public RouterFunction<ServerResponse> routerCart(CartHandler cartHandler) {
    return route()
        .GET(cartProperties.getRoot(), cartHandler::getCartContent)
        .POST(cartProperties.getRoot().concat("/").concat(cartProperties.getAdd()), cartHandler::addProduct)
        .DELETE(cartProperties.getRoot().concat("/{").concat(cartProperties.getAllContentById()).concat("}"), cartHandler::removeAllCartContent)
        .build();
  }

}
