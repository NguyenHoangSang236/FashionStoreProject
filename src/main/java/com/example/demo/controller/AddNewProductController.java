package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddNewProductController {
    @GetMapping("/addproduct")
    public String addproduct(Model model) {
        return "add-product";
    }
}
