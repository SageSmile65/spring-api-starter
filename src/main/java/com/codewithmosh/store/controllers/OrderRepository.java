package com.codewithmosh.store.controllers;

import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}