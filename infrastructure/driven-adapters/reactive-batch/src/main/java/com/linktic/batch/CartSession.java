package com.linktic.batch;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CartSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  private Long cartId;
  private String user;
  private Double total;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
