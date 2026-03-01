package com.codewithmosh.store.Mapper;

import com.codewithmosh.store.dtos.OrderItemDto;
import com.codewithmosh.store.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {

    OrderItemDto toOrderItemDto(OrderItem orderItem);
}
