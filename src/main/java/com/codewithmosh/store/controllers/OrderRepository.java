package com.codewithmosh.store.controllers;

import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
import com.codewithmosh.store.entities.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByCustomerId(Long id);


    @EntityGraph(attributePaths = "orderItems.product")
    List<Order> findAllByCustomerId(Long id);

    @EntityGraph(attributePaths = "orderItems.product")
    Optional<Order> findById(Long id);
}