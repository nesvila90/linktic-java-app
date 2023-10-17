package com.linktic.api.commons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.cart")
public class CartProperties {

  private String root;
  private String add;
  private String allContentById;

}
