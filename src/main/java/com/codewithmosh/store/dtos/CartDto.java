package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


@Data
@AllArgsConstructor
public class CartDto implements Serializable {
    private UUID id;
    private Set<CartItemDto> items = new  LinkedHashSet<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}