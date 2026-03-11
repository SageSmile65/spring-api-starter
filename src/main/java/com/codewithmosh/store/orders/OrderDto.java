package com.codewithmosh.store.orders;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

@Data
public class OrderDto {
    private Long id;
    private String status;
    private LocalDateTime createdAt;
    private HashSet<OrderItemDto> items;
    private BigDecimal totalPrice;

}
