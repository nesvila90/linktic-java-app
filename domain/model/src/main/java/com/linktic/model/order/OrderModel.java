package com.linktic.model.order;

import com.linktic.model.cart.item.CartModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class OrderModel {

  private Long id;
  private CartModel productsPurchased;
  private Double totalPrice;


}
