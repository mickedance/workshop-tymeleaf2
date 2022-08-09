package com.example.workshoptymeleaf2.converter;

import com.example.workshoptymeleaf2.dto.ProductView;
import com.example.workshoptymeleaf2.entity.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ProductConverter implements Converter<Product, ProductView> {


    @Override
    public ProductView entityToView(Product entity) {
        if(entity==null) return null;
        return new ProductView(entity.getId(), entity.getName(), entity.getCategory().getId(), entity.getCreatedDate());
    }

    @Override
    public List<ProductView> entitiesToViews(List<Product> entities) {
        return entities.stream().map(this::entityToView).collect(Collectors.toList());
    }

    @Override
    public Product viewToEntity(ProductView view) {
        return null;
    }

    @Override
    public List<Product> viewsToEntities(List<ProductView> views) {
        return null;
    }
}
