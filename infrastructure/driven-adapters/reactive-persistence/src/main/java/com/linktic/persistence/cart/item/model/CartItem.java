package com.linktic.persistence.cart.item.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CartItem {

  @Id
  private Long id;
  private Long cartId;
  private Long productId;
  private Integer quantity;
  private LocalDateTime cartItemAddedDate;


}
