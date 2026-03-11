package com.codewithmosh.store.carts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto implements Serializable {
    private Long id;
    private CartProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
}