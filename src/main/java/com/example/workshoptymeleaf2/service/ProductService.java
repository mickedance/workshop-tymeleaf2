package com.example.workshoptymeleaf2.service;

import com.example.workshoptymeleaf2.dto.ProductForm;
import com.example.workshoptymeleaf2.dto.ProductView;
import com.example.workshoptymeleaf2.entity.Product;
import com.example.workshoptymeleaf2.exception.DuplicateEntityException;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductView save(ProductForm form);
    List<ProductView> findAll();
    ProductView findById(int id);
    boolean deleteById(int id);
    boolean update(ProductForm form) throws DuplicateEntityException;
    Optional<Product> findByName(String name);
    boolean duplicateNameExists(ProductForm form);
}
