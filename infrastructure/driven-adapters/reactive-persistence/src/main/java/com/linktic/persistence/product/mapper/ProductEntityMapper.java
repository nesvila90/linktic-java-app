package com.linktic.persistence.product.mapper;

import com.linktic.model.product.ProductModel;
import com.linktic.model.product.Status;
import com.linktic.persistence.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductEntityMapper {

  @Mapping(source = "status", target = "status", qualifiedByName = "statusModel")
  ProductModel toModel(Product data);


  @Named("statusModel")
  default Status statusToStatusModel(String item) {
    return Status.setStatusFromString(item).get();
  }

}
