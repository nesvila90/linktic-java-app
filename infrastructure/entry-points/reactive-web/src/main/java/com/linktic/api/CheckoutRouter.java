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
public class CheckoutRouter {

  private final CartProperties cartProperties;

  @Bean
  public RouterFunction<ServerResponse> routerCheckout(CkeckoutHandler ckeckoutHandler) {
    return route()
        .PUT(cartProperties.getRoot(), ckeckoutHandler::checkout)
        .build();
  }

}
