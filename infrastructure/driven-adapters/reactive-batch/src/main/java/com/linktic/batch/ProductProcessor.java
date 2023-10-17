package com.linktic.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class ProductProcessor implements ItemProcessor<Product, Product> {

  @Override
  public Product process(Product item) {
    return item;
  }
}
