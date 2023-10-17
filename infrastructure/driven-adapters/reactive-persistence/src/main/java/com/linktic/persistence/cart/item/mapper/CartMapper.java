package com.linktic.persistence.cart.item.mapper;


import com.linktic.model.cart.item.CartModel;
import com.linktic.persistence.cart.item.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {

  CartModel toModel(CartItem cartItem);

}
