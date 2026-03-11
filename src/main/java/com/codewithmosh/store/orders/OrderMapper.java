package com.codewithmosh.store.orders;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "items",source = "orderItems")
    OrderDto toOrderDto(Order order);
}
