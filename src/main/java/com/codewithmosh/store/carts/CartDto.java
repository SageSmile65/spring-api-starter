package com.codewithmosh.store.carts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


@Data
@AllArgsConstructor
public class CartDto implements Serializable {
    private UUID id;
    private Set<CartItemDto> items = new  LinkedHashSet<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}