package com.example.workshoptymeleaf2.Controller;

import com.example.workshoptymeleaf2.dto.CategoryForm;
import com.example.workshoptymeleaf2.dto.ProductForm;
import com.example.workshoptymeleaf2.dto.ProductView;
import com.example.workshoptymeleaf2.entity.Product;
import com.example.workshoptymeleaf2.exception.DuplicateEntityException;
import com.example.workshoptymeleaf2.service.CategoryService;
import com.example.workshoptymeleaf2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;
    private String initProductName;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String defaultPage(Model model) {
        model.addAttribute("title", "Products");
        model.addAttribute("products", productService.findAll());
        return "product/product-list";
    }

    @GetMapping("/")
    public String getDefaultPage() {
        return "redirect:/product/list";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("title", "Add Product");
        model.addAttribute("form", new ProductForm());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("postUrl", "add");
        return "product/product-form";
    }

    @PostMapping("/add")
    public String addProduct(Model model, @ModelAttribute("form") @Valid ProductForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        model.addAttribute("postUrl", "add");
        model.addAttribute("title", "Add Product");
        if (bindingResult.hasErrors()) {
            System.out.println("errores:" + bindingResult.getAllErrors());
            return "product/product-form";
        }
        form.setCategoryId(1);
        productService.save(form);
        redirectAttributes.addFlashAttribute("success", "Product " + form.getName() + " added");

        return "redirect:/product/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (!productService.deleteById(id)) {
            redirectAttributes.addFlashAttribute("error", "Could not delete product Id " + id);
        } else
            redirectAttributes.addFlashAttribute("success", "Product deleted");

        return "redirect:/product/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("title", "Edit Product");
        model.addAttribute("postUrl", "update");
        model.addAttribute("categories", categoryService.findAll());
        ProductView productView = productService.findById(id);
        model.addAttribute("initName", initProductName = productView.getName());
        if (productView == null) return "redirect:/product/list";
        ProductForm form = new ProductForm(productView.getId(), initProductName, productView.getCategoryId(), productView.getCreatedDate());
        model.addAttribute("form", form);
        return "product/product-form";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("form") @Valid ProductForm form, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        model.addAttribute("title", "Edit Product");
        model.addAttribute("postUrl", "update");
        model.addAttribute("initName", initProductName);
        model.addAttribute("categories", categoryService.findAll());

        try {
            boolean updated = productService.update(form);
            if (!updated) {
                redirectAttributes.addFlashAttribute("error", "Product could not be updated");

            } else
                redirectAttributes.addFlashAttribute("success", "Product updated successfully");
        } catch (DuplicateEntityException e) {
            FieldError error = new FieldError("form", "name", "Product " + form.getName() + " already exists");
            bindingResult.addError(error);
        }
        if (bindingResult.hasErrors()) {
            System.out.println("former::" + form);
            return "product/product-form";
        }
        return "redirect:/product/list";
    }

    @GetMapping("/details/{id}")
    public String viewDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("title", "Product Details");
        ProductView productView = productService.findById(id);
        if (productView == null) {
            model.addAttribute("error", "Could not found product");
        }
        model.addAttribute("view", productView);
        model.addAttribute("category", categoryService.findById(productView.getCategoryId()).getName());
        return "product/product-details";
    }
}
