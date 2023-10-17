package com.linktic.model.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ProductModel {

  private long id;
  private String name;
  private String brand;
  private Double price;
  private Integer stockQuantity;
  private Status status;
  private Double discountPercentage;

}
