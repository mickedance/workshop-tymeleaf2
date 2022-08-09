package com.example.workshoptymeleaf2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String defaultPage(Model model) {
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/")
    public String redirectToToDefaultPage() {
        return "redirect:/home";
    }

}
