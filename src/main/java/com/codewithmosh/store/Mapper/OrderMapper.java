package com.codewithmosh.store.Mapper;

import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "items",source = "orderItems")
    OrderDto toOrderDto(Order order);
}
