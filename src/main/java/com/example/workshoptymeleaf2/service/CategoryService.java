package com.example.workshoptymeleaf2.service;

import com.example.workshoptymeleaf2.dto.CategoryForm;
import com.example.workshoptymeleaf2.dto.CategoryView;
import com.example.workshoptymeleaf2.entity.Category;

import java.util.List;

public interface CategoryService {
    CategoryView findById(int id);

    List<CategoryView> findAll();

    CategoryView save(CategoryForm form);

    boolean deleteById(int id);

    boolean update(CategoryForm form);

}
