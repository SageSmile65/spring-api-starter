package com.codewithmosh.store.Mapper;

import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "items",source = "cartItems")
    @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    CartDto toCartDto(Cart cart);
}
