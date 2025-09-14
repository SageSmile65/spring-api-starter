package com.codewithmosh.store.Mapper;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    //Mapping category.id to categoryId because there is not catId field in product entity
    @Mapping(source = "category.id",target = "categoryId")
    ProductDto toProductDto(Product product);
}
