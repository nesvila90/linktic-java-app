package com.linktic.batch;

import static com.linktic.model.product.FieldName.BRAND;
import static com.linktic.model.product.FieldName.DISCOUNT_PERCENTAGE;
import static com.linktic.model.product.FieldName.NAME;
import static com.linktic.model.product.FieldName.PRICE;
import static com.linktic.model.product.FieldName.STATUS;
import static com.linktic.model.product.FieldName.STOCK_QUANTITY;
import static com.linktic.model.product.Status.setStatusFromString;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;

@Component
public class ProductFieldSetMapper implements FieldSetMapper<Product> {

  @Override
  public Product mapFieldSet(FieldSet fieldSet) {
    return Product.builder()
        .name(fieldSet.readString(NAME.getFieldName()))
        .brand(fieldSet.readString(BRAND.getFieldName()))
        .price(fieldSet.readDouble(PRICE.getFieldName()))
        .stockQuantity(fieldSet.readInt(STOCK_QUANTITY.getFieldName()))
        .status(setStatusFromString(fieldSet.readString(STATUS.getFieldName())).get().getDescriptionName())
        .discountPercentage(fieldSet.readDouble(DISCOUNT_PERCENTAGE.getFieldName()))
        .build();
  }
}
