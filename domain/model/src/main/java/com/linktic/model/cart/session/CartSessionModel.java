package com.linktic.model.cart.session;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartSessionModel {

  private Long cartId;
  private String user;
  private Double total;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
