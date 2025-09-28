package com.codewithmosh.store.repositories;

import com.codewithmosh.store.entities.Cart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartRepository extends CrudRepository<Cart, UUID> {
}