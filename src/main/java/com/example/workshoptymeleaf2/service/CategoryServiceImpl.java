package com.example.workshoptymeleaf2.service;

import com.example.workshoptymeleaf2.converter.CategoryConverter;
import com.example.workshoptymeleaf2.dto.CategoryForm;
import com.example.workshoptymeleaf2.dto.CategoryView;
import com.example.workshoptymeleaf2.entity.Category;
import com.example.workshoptymeleaf2.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    CategoryRepository categoryRepository;
    CategoryConverter categoryConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryConverter categoryConverter){
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    @Override
    public List<CategoryView> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryConverter.entitiesToViews(categories);
    }

    public CategoryView save(CategoryForm form) {
        if(form == null) throw new IllegalArgumentException("category form was null");
        Category entity = new Category(form.getName());
        Category savedEntity = categoryRepository.save(entity);
        return null;
    }

    public CategoryView findById(int id){
        Category category = categoryRepository.findById(id).orElse(null);
        if(category==null)
            return null;
        return categoryConverter.entityToView(category);
    }
    public boolean deleteById(int id){
        if(findById(id) ==null) return false;
        categoryRepository.deleteById(id);
        return true;
    }

    public boolean update(CategoryForm form){
        System.out.println(form);
        Category category = categoryRepository.findById(form.getId()).orElse(null);
        if(category==null) return false;

        category.setName(form.getName());
        Category savedCategory = categoryRepository.save(category);
        if(savedCategory ==null) return false;

        return true;
    }
}
