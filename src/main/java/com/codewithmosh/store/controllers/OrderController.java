package com.codewithmosh.store.controllers;

import com.codewithmosh.store.Mapper.OrderMapper;
import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    @GetMapping
    public ResponseEntity<?> getOrders() {
        var customerId = authService.getCurrentUser().getId();
        var orders = orderRepository.findAllByCustomerId(customerId);

        List<OrderDto> allOrders = orders.stream().map(orderMapper::toOrderDto).toList();

        return ResponseEntity.ok(allOrders);
    }
}