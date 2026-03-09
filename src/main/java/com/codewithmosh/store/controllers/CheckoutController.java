package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.CheckoutRequest;
import com.codewithmosh.store.dtos.CheckoutResponse;
import com.codewithmosh.store.dtos.ErrorDto;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
import com.codewithmosh.store.entities.OrderStatus;
import com.codewithmosh.store.exceptions.CartNotFoundException;
import com.codewithmosh.store.exceptions.EmptyCartException;
import com.codewithmosh.store.exceptions.PaymentException;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.services.AuthService;
import com.codewithmosh.store.services.CartService;
import com.codewithmosh.store.services.CheckoutService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.net.Webhook;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
@Data
public class CheckoutController{

    private final CheckoutService checkoutService;

    @Value("${stripe-webhookSecretyKey}")
    private String webhookSecretKey;

    @PostMapping
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutRequest request){
        var cartId = request.getCart_id();
        if(cartId == null){
            return ResponseEntity.badRequest().body("Cart id is null");
        }
        var response = checkoutService.checkout(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestHeader("Stripe-Singature") String signature,
                                              @RequestBody String payload
    ){
        try {
            var event = Webhook.constructEvent(payload,signature,webhookSecretKey);
            System.out.println(event.getType());
            var paymentObject = event.getDataObjectDeserializer().getObject();
            switch (event.getType()) {
                case "payment_intent. succeeded" -> {
                    // Update order status (PAID)
                }
                case "payment_intent. failed" -> {
                    // Update order status (FAILED);
                }
            }
            return ResponseEntity.ok().build();
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(PaymentException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFoundException(CartNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(e.getMessage()));
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<?> handleEmptyCartException(EmptyCartException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(e.getMessage()));
    }
}