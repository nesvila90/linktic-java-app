package com.linktic.api.util;

import com.linktic.api.commons.CartProperties;
import com.linktic.api.commons.ProductCatalogProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
    ProductCatalogProperties.class,
    CartProperties.class,
})
public class RoutesProperties {

}
