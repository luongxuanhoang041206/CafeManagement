package com.example.demo.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebViewController {

    @GetMapping("/online-order")
    public String viewOrderPage() {
        return "onlineOrder"; 
    }
}