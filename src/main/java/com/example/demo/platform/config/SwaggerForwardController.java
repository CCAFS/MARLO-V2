package com.example.demo.platform.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerForwardController {

    @GetMapping("/swagger-ui")
    public String forwardSwaggerUi() {
        return "forward:/swagger-ui/index.html";
    }
}
