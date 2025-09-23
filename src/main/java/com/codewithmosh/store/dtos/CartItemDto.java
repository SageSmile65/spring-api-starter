package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto implements Serializable {
    private Long id;
    private CartDto cart;
    private ProductDto product;
    private Integer quantity;
}