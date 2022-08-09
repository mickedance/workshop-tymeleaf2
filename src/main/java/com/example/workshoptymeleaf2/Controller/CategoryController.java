package com.example.workshoptymeleaf2.Controller;

import com.example.workshoptymeleaf2.dto.CategoryForm;
import com.example.workshoptymeleaf2.dto.CategoryView;
import com.example.workshoptymeleaf2.entity.Category;
import com.example.workshoptymeleaf2.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedNotification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {

    CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String getDefaultPage(Model model){
        model.addAttribute("title", "Categories");
        model.addAttribute("categories", categoryService.findAll());
        return "category/category-list";
    }
    @GetMapping("/")
    public String redirectToDefaultPage(){
        return "redirect:list";
    }

    @GetMapping("/form")
    public String form(Model model){
        model.addAttribute("title", "Add Category");
        model.addAttribute("postUrl", "add");
        model.addAttribute("form", new CategoryForm() );
        return "category/category-form";
    }
    @PostMapping("/add")
    public String addCategory(@ModelAttribute("form") @Valid CategoryForm form, BindingResult bindingResult,Model model){
        model.addAttribute("title", "Add Category");
        model.addAttribute("postUrl", "add");
        if(bindingResult.hasErrors()){
            return "category/category-form";
        }
        categoryService.save(form);
        return "redirect:list";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes){
        boolean result = categoryService.deleteById(id);
        if(!result)
            redirectAttributes.addFlashAttribute("error", "Could not delete category ID:" + id );
        else
            redirectAttributes.addFlashAttribute("success", "Category ID:" + id+ " deleted" );

        return "redirect:/category/list";
    }
    @GetMapping("/details/{id}")
    public String viewDetailsById(@PathVariable("id") int id, Model model ){
        model.addAttribute("title", "Details");
        CategoryView categoryView = categoryService.findById(id);
        System.out.println(categoryView);
        model.addAttribute("view", categoryView);
        return "category/category-details";
    }

    @GetMapping("/edit/{id}")
    public String editById(@PathVariable("id") int id, Model model){
        model.addAttribute("title", "Update");
        model.addAttribute("postUrl", "update");

        CategoryView categoryView = categoryService.findById(id);
        model.addAttribute("form", new CategoryForm(categoryView.getId(), categoryView.getName()));

        return "category/category-form";
    }
    @PostMapping("/update")
    public String updateCategory(@Valid @ModelAttribute("form") CategoryForm form, RedirectAttributes redirectAttributes){
        if(categoryService.update(form))
            redirectAttributes.addFlashAttribute("success", "Category "+ form.getName()+ " updated");
        else
            redirectAttributes.addFlashAttribute("error", "Category "+ form.getName()+ " could not be updated");

        return "redirect:/category/list";
    }
}
