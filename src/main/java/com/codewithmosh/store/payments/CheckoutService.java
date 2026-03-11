package com.codewithmosh.store.payments;

import com.codewithmosh.store.controllers.OrderRepository;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.EmptyCartException;
import com.codewithmosh.store.exceptions.PaymentException;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.services.AuthService;
import com.codewithmosh.store.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request){
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
        try{
            var session = paymentGateway.createCheckoutSession(order);

            cartService.clearCart(cartId);
            return new CheckoutResponse(order.getId(),session.getCheckoutUrl());
        }
        catch (PaymentException ex){
            System.out.println(ex.getMessage());
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handleWebhookEvent(WebhookRequest webhookRequest){
        paymentGateway.parseWebhookRequest(webhookRequest).ifPresent(paymentResult->{
            var orderId =  paymentResult.getOrderId();
            var order = orderRepository.findById(orderId).orElseThrow();
            order.setStatus(PaymentStatus.PAID);
            orderRepository.save(order);
        });

    }
}
