package com.clubdalulu.controle_estoque.shared.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/web/produtos";
    }
}
