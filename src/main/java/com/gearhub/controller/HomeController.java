package com.gearhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "paginas/home";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "paginas/sobre";
    }
}
