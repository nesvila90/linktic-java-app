package com.linktic.api.request;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class FiltersDTO {

  String brand;
  String name;
  String initialPriceRange;
  String finalPriceRange;
  String pageNumber;
  String pageSize;


}
