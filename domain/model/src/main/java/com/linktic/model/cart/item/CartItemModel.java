package com.linktic.model.cart.item;

import com.linktic.model.inventory.InventoryModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class CartItemModel {

  private Long cartId;
  private InventoryModel product;
}
