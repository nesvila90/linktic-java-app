package com.linktic.persistence.cart.session.mapper;


import com.linktic.model.cart.session.CartSessionModel;
import com.linktic.persistence.cart.session.model.CartSession;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartSessionMapper {

  CartSessionModel toModel(CartSession cartSession);

}
