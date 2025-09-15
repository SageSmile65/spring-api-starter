package com.codewithmosh.store.controllers;

import com.codewithmosh.store.Mapper.ProductMapper;
import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(
            @RequestParam(required = false, name = "categoryId") Byte categoryId
    ){
        List<Product> products;
        if(categoryId != null){
            products = productRepository.findAllByCategory_Id(categoryId);
        }
        else{
            products = productRepository.findAllWithCategory();
        }
        return products.stream()
                .map(productMapper::toProductDto)
                .toList();

    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        var product = productRepository.findById(id).orElse(null);
        var productDto = productMapper.toProductDto(product);
        if(productDto == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDto);
    }
}
