package com.codewithmosh.store.orders;

import com.codewithmosh.store.exceptions.OrderNotFoundException;
import com.codewithmosh.store.exceptions.WrongfulOrderMapping;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<?> getOrders() {
        var allOrders = orderService.findAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("orderId") Long orderId) {
        var order = orderService.getOrder(orderId);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(order);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> handleOrderNotFoundException(OrderNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("error: "+e.getMessage());
    }

    @ExceptionHandler(WrongfulOrderMapping.class)
    public ResponseEntity<?> handleWrongfulOrderMapping(WrongfulOrderMapping e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error: "+e.getMessage());
    }
}