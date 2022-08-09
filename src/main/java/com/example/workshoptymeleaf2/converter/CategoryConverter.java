package com.example.workshoptymeleaf2.converter;

import com.example.workshoptymeleaf2.dto.CategoryView;
import com.example.workshoptymeleaf2.entity.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class CategoryConverter implements Converter<Category, CategoryView> {

    public CategoryView entityToView(Category category){
        if(category==null) throw new IllegalArgumentException("category was null");
        return new CategoryView(category.getId(), category.getName());
    }
    public List<CategoryView> entitiesToViews(List<Category> categoryList){

        if(categoryList==null) throw new IllegalArgumentException("categories was null");

        return categoryList.stream().map(this::entityToView).collect(Collectors.toList());
    }

    public Category viewToEntity(CategoryView categoryView){
        return new Category(categoryView.getId(), categoryView.getName());
    }

    public List<Category> viewsToEntities(List<CategoryView> categoryViews){
        return categoryViews.stream().map(this::viewToEntity).collect(Collectors.toList());
    }
}
