package com.linktic.persistence.cart.session.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class CartSession {

  @Id
  private Long cartId;
  private String user;
  private Double total;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
