package com.example.workshoptymeleaf2.repository;

import com.example.workshoptymeleaf2.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    List<Category> findAll();
}
