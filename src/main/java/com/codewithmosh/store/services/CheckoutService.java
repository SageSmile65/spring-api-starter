package com.codewithmosh.store.services;

import com.codewithmosh.store.controllers.OrderRepository;
import com.codewithmosh.store.dtos.CheckoutRequest;
import com.codewithmosh.store.dtos.CheckoutResponse;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.EmptyCartException;
import com.codewithmosh.store.repositories.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public CheckoutResponse checkout(CheckoutRequest request) {
        var cartId = request.getCart_id();
        var cart = cartRepository.findById(request.getCart_id()).orElse(null);
        if(cart == null){
            throw new CartNotFoundException("Cart not found");
        }
        if(cart.isEmpty()){
            throw new EmptyCartException("Cart is empty");
        }
        var order = Order.createFromCart(cart,authService.getCurrentUser());
        orderRepository.save(order);
        cartService.clearCart(cartId);
        return new CheckoutResponse(order.getId());
    }
}
