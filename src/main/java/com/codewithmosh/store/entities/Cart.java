package com.codewithmosh.store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created",insertable = false,updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL,orphanRemoval = true, fetch =  FetchType.EAGER)
    private Set<CartItem> cartItems = new LinkedHashSet<>();

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (CartItem cartItem : cartItems) {
            totalPrice = totalPrice.add(cartItem.getTotalPrice());
        }
        return totalPrice;
    }
    public CartItem getCartItemById(Long itemId) {
        for(CartItem item : cartItems){
            if(item.getId().equals(itemId)){
                return  item;
            }
        }
        return null;
    }
    public CartItem getCartItemByProductId(Long productId){
        for(CartItem item :  cartItems) {
            if(item.getProduct().getId().equals(productId)) {
                return item;
            }
        }
        return null;
    }
}