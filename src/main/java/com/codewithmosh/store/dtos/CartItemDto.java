package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapping;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto implements Serializable {
    private CartProductDto product;
    private int quantity;
    private BigDecimal totalPrice;
}