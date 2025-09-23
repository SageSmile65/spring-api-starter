package com.codewithmosh.store.dtos;

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
    private Long id;
    LocalDate dateCreated;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;

}