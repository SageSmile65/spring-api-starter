package com.codewithmosh.store.orders;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {

    private OrderProductDto product;
    private Long quantity;
    private BigDecimal totalPrice;
}
