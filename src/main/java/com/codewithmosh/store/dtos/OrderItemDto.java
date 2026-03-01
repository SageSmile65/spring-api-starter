package com.codewithmosh.store.dtos;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderItemDto {

    private OrderProductDto product;
    private Long quantity;
    private BigDecimal totalPrice;
}
