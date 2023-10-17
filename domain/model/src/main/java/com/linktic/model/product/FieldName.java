package com.linktic.model.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum FieldName {


  ID("id"),
  NAME("nombre"),
  BRAND("marca"),
  PRICE("precio"),
  STOCK_QUANTITY("cantidad en stock"),
  STATUS("estado"),
  DISCOUNT_PERCENTAGE("porcentaje descuento");

  private String fieldName;


}
