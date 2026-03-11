package com.codewithmosh.store.carts;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "items",source = "cartItems")
    @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    CartDto toCartDto(Cart cart);
}
