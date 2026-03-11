package com.codewithmosh.store.orders;

import com.codewithmosh.store.auth.AuthService;
import com.codewithmosh.store.exceptions.OrderNotFoundException;
import com.codewithmosh.store.exceptions.WrongfulOrderMapping;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> findAllOrders() {
        var customerId = authService.getCurrentUser().getId();
        var orders = orderRepository.findAllByCustomerId(customerId);

        List<OrderDto> allOrders = orders.stream().map(orderMapper::toOrderDto).toList();
        return allOrders;
    }

    public OrderDto getOrder(Long orderId) {
        var order = orderRepository.findById(orderId).orElse(null);
        if(order == null) {
            throw new OrderNotFoundException("Order not found");
        }
        var customer = order.getCustomer();
        if(customer.getId() != authService.getCurrentUser().getId()){
            throw new WrongfulOrderMapping("Order belongs to another user");
        }
        return orderMapper.toOrderDto(order);
    }
}