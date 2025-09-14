package com.codewithmosh.store.controllers;

import com.codewithmosh.store.Mapper.ProductMapper;
import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(){

        var products =  productRepository.findAll().stream()
                .map(productMapper::toProductDto)
                .toList();
        return products;
    }
}
