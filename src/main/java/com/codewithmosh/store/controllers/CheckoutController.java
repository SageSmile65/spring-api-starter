package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.CheckoutRequest;
import com.codewithmosh.store.dtos.CheckoutResponse;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
import com.codewithmosh.store.entities.OrderStatus;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.services.AuthService;
import com.codewithmosh.store.services.CartService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    public CheckoutController(CartRepository cartRepository, AuthService authService, OrderRepository orderRepository, CartService cartService) {
        this.cartRepository = cartRepository;
        this.authService = authService;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request) {
        var cartId = request.getCart_id();
        if(cartId == null){
            return ResponseEntity.badRequest().body(Map.of("error","cart id is required"));
        }
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        if(cart.getCartItems().isEmpty()){
            return ResponseEntity.badRequest().body(Map.of("error","cart is empty!"));
        }
        Order order = Order.builder()
                .customer(authService.getCurrentUser())
                .status(OrderStatus.PENDING)
                .totalPrice(cart.getTotalPrice())
                .createdAt(LocalDateTime.now())
                .build();
        cart.getCartItems().forEach(item -> {
            var orderItem = new OrderItem();
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setTotalPrice(item.getTotalPrice());
            order.getOrderItems().add(orderItem);
            orderItem.setOrder(order);
        });
        orderRepository.save(order);
        cartService.clearCart(cartId);
        return ResponseEntity.ok().body(new CheckoutResponse(order.getId()));
    }
}
