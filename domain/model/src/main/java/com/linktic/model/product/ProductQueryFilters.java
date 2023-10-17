package com.linktic.model.product;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ProductQueryFilters {

  private String brand;
  private String name;
  private String initialPriceRange;
  private String finalPriceRange;
  private int pageNumber;
  private int pageSize;


}
