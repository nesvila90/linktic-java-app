package com.linktic.api.commons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.product-catalog")
public class ProductCatalogProperties {

  private String root;

}
