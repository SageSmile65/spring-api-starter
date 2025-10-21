package com.codewithmosh.store.Mapper;

import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItem toEntity(AddItemToCartRequest request);

    @Mapping(source = "cart.id",target = "cartId")
    @Mapping(source = "product.id",target = "productId")
    CartItemDto toCartItemDto(CartItem cartItem);
}
