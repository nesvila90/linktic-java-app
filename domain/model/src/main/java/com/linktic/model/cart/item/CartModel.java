package com.linktic.model.cart.item;

import com.linktic.model.product.ProductModel;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CartModel {

  private Long id;
  private Long cartId;
  private Long productId;
  private Integer quantity;
  private LocalDateTime cartItemAddedDate;
  private ProductModel product;

}
