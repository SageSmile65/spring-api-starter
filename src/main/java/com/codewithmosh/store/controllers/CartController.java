package com.codewithmosh.store.controllers;

import com.codewithmosh.store.Mapper.CartItemMapper;
import com.codewithmosh.store.Mapper.CartMapper;
import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriComponentsBuilder) {
        var cart = new  Cart();
        cartRepository.save(cart);
        var cartDto = cartMapper.toCartDto(cart);
        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return ResponseEntity.created(uri).body(cartDto);
    }
    @PostMapping("/{id}")
    public ResponseEntity<CartItemDto> addToCart(@RequestBody AddItemToCartRequest request,
                                                 @PathVariable UUID id,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        var cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            return ResponseEntity.notFound().build();
        }
        var product = productRepository.findById(request.getProductId()).orElse(null);
        if (product == null) {
            return ResponseEntity.badRequest().build();
        }
        for(CartItem cartItem : cart.getCartItems()) {
            if(cartItem.getProduct().getId().equals(product.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
                cartRepository.save(cart);
                return ResponseEntity.ok(cartItemMapper.toCartItemDto(cartItem));
            }
        }
        var cartItem = cartItemMapper.toEntity(request);
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);

        var uri = uriComponentsBuilder.path("/carts/{id}").buildAndExpand(cartItem.getId()).toUri();
        return ResponseEntity.created(uri).body(cartItemMapper.toCartItemDto(cartItem));
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(cartMapper.toCartDto(cart));
    }

    @PutMapping("/{cartId}/cart_items/{itemId}")
    public ResponseEntity<CartItemDto> updateCart(@PathVariable UUID cartId,
                                                  @PathVariable Long itemId,
                                                  @RequestBody UpdateCartItemRequest request){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        CartItem cartItem = null;
        for(CartItem item : cart.getCartItems()){
            if(item.getId().equals(itemId)){
                cartItem = item;
                break;
            }
        }
        if(cartItem == null){
            return ResponseEntity.notFound().build();
        }
        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);

        return  ResponseEntity.ok(cartItemMapper.toCartItemDto(cartItem));
    }
}