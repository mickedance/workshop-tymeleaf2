package com.example.workshoptymeleaf2.service;

import com.example.workshoptymeleaf2.converter.ProductConverter;
import com.example.workshoptymeleaf2.dto.ProductForm;
import com.example.workshoptymeleaf2.dto.ProductView;
import com.example.workshoptymeleaf2.entity.Category;
import com.example.workshoptymeleaf2.entity.Product;
import com.example.workshoptymeleaf2.exception.DuplicateEntityException;
import com.example.workshoptymeleaf2.repository.CategoryRepository;
import com.example.workshoptymeleaf2.repository.ProductRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private ProductConverter productConverter;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductView save(ProductForm form) {
        Category category = categoryRepository.findById(form.getCategoryId()).orElse(null);
        if (category == null) throw new IllegalArgumentException("category invalid");
        Product product = new Product(form.getName(), category);
        Product savedProduct = productRepository.save(product);
        return productConverter.entityToView(savedProduct);
    }

    @Override
    public List<ProductView> findAll() {
        return productConverter.entitiesToViews(productRepository.findAll());
    }

    @Override
    public ProductView findById(int id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return null;
        return productConverter.entityToView(product);
    }

    @Override
    public boolean deleteById(int id) {
        ProductView productView = findById(id);
        if (productView == null) return false;
        productRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean update(ProductForm form) throws DuplicateEntityException {
        if (duplicateNameExists(form)) throw new DuplicateEntityException(form.getName() + " already exists");
        Product product = productRepository.findById(form.getId()).orElse(null);
        System.out.println("pr" + product);
        if (product == null)
            return false;
        Category category = categoryRepository.findById(form.getCategoryId()).orElse(null);
        if (category == null) return false;

        product.setCategory(category);
        product.setName(form.getName());
        Product updatedProduct = productRepository.save(product);

        if (updatedProduct == null) return false;

        return true;
    }

    @Override
    public Optional<Product> findByName(String name) {
        Product product = productRepository.findByName(name);
        return Optional.ofNullable(product);
    }

    @Override
    public boolean duplicateNameExists(ProductForm form) {
        Optional<Product> productOptional = findByName(form.getName());
        if (productOptional.isPresent()) {
            if (productOptional.get().getId() != form.getId()) {
                return true;
            }
        }
        return false;
    }
}
