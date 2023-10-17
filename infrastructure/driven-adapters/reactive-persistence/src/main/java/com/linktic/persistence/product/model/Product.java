package com.linktic.persistence.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {

  @Id
  private Long id;
  private String name;
  private String brand;
  private Double price;
  private Integer stockQuantity;
  private String status;
  private Double discountPercentage;

}
