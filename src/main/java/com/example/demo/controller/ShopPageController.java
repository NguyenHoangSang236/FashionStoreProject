package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopPageController {
    @GetMapping("/shop")
    public String showHomePage() {
        return "shop";
    }
    
    
}
