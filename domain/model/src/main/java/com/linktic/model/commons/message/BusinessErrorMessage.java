package com.linktic.model.commons.message;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = PRIVATE)
public enum BusinessErrorMessage {

  INVALID_STATUS("BU_0001", "Status not founded."),
  PRODUCT_NOT_FOUNDED("PB_0001", "Product not founded."),
  MISSING_REQUIRED_FIELD("PB_0002", "Parameters not found"),
  NOT_ENOUGH_STOCK("PB_0003", "Stock no available."),
  CART_NOT_FOUNDED("PB_0004", "Cart not founded."),
  CART_ITEM_ADD_ERROR("PB_0005", "Cart item added Error."),
  CART_SESSION_NOT_FOUNDED("PB_0005", "Cart session not founded."), CHECKOUT_ERROR("PB_0006", "Checkout errro");

  private final String code;
  private final String message;

}
