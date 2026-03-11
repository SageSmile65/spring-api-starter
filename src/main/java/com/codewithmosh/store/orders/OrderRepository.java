package com.codewithmosh.store.orders;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByCustomerId(Long id);


    @EntityGraph(attributePaths = "orderItems.product")
    List<Order> findAllByCustomerId(Long id);

    @EntityGraph(attributePaths = "orderItems.product")
    Optional<Order> findById(Long id);
}