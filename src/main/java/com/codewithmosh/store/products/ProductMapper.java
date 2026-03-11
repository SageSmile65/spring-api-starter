package com.codewithmosh.store.products;

import com.codewithmosh.store.carts.CartProductDto;
import com.codewithmosh.store.orders.OrderProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    //Mapping category.id to categoryId because there is not catId field in product entity
    @Mapping(source = "category.id",target = "categoryId")
    ProductDto toProductDto(Product product);

    //Takes productDto json and converts it to product
    Product toEntity(ProductDto productDto);

    //Update product
    void updateProduct(ProductDto productDto, @MappingTarget Product product);

    CartProductDto toCartProductDto(Product product);

    //Creating a product dto for getting orders
    OrderProductDto toOrderProductDto(Product product);

}