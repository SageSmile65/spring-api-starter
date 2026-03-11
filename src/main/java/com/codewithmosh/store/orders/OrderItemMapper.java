package com.codewithmosh.store.orders;

import com.codewithmosh.store.products.ProductMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {

    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
