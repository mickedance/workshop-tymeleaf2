package com.example.workshoptymeleaf2.repository;

import com.example.workshoptymeleaf2.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findAll();
    Product findByName(String name);
}
