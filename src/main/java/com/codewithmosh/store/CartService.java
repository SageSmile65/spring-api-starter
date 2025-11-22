package com.codewithmosh.store;

import com.codewithmosh.store.Mapper.CartItemMapper;
import com.codewithmosh.store.Mapper.CartMapper;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.CartItem;
import com.codewithmosh.store.exceptions.CartItemNotFoundException;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.ProductNotFoundException;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Data
public class CartService {
    private final CartMapper cartMapper;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemMapper cartItemMapper;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        var cartDto = cartMapper.toCartDto(cart);
        return cartDto;
    }
    public CartItemDto addToCart(UUID id, Long productId,int quantity){
        var cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        var product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException();
        }
        for(CartItem cartItem : cart.getCartItems()) {
            if(cartItem.getProduct().getId().equals(product.getId())) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartRepository.save(cart);
                return cartItemMapper.toCartItemDto(cartItem);
            }
        }
        var cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cart.getCartItems().add(cartItem);
        cartRepository.save(cart);
        return cartItemMapper.toCartItemDto(cartItem);
    }
    public CartDto getCart(UUID id){
        var cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        return cartMapper.toCartDto(cart);
    }

    public CartItemDto updateCart(UUID cartId, Long itemId,int quantity){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }
        CartItem cartItem = null;
        cartItem = cart.getCartItemById(itemId);
        if(cartItem == null){
            throw  new CartItemNotFoundException();
        }
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartItemMapper.toCartItemDto(cartItem);
    }

    public CartDto deleteProduct(UUID cartId,Long productId){
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new  CartNotFoundException();
        }
        CartItem cartItem = null;
        cartItem = cart.getCartItemByProductId(productId);
        if (cartItem == null) {
            throw  new CartItemNotFoundException();
        }
        cart.getCartItems().remove(cartItem);
        cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    public void clearCart(UUID cartId){
        var cart =  cartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

}
