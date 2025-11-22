package com.codewithmosh.store.controllers;

import com.codewithmosh.store.CartService;
import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;
import com.codewithmosh.store.exceptions.CartItemNotFoundException;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriComponentsBuilder) {
        var cartDto = cartService.createCart();
        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }
    @PostMapping("/{id}")
    public ResponseEntity<CartItemDto> addToCart(@RequestBody AddItemToCartRequest request,
                                                 @PathVariable UUID id,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        var cartItem = cartService.addToCart(id,request.getProductId(),request.getQuantity());

        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartItem.getId()).toUri();
        return ResponseEntity.created(uri).body(cartItem);
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId){
        var cartDto =  cartService.getCart(cartId);
        return  ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<?> updateCart(@PathVariable UUID cartId,
                                                  @PathVariable Long itemId,
                                                  @RequestBody UpdateCartItemRequest request){
        var cartItem = cartService.updateCart(cartId,itemId,request.getQuantity());

        return  ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID cartId,
                                                 @PathVariable Long productId) {
        var cartDto = cartService.deleteProduct(cartId,productId);
        return ResponseEntity.ok(cartDto);
    }
    @DeleteMapping("/{cartId}")
    public void clearCart(@PathVariable UUID cartId){
        cartService.clearCart(cartId);
    }
    @ExceptionHandler(CartNotFoundException.class)
    public  ResponseEntity<Map<String,String>> cartNotFound(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Cart not found"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> productNotFound(ProductNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Product not found"));
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<Map<String,String>> cartItemNotFound(CartItemNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error","Cart Item not found"));
    }
}