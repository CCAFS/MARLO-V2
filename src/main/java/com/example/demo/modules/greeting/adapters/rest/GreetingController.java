package com.example.demo.modules.greeting.adapters.rest;

import com.example.demo.modules.greeting.application.port.inbound.GreetingUseCase;
import com.example.demo.modules.greeting.domain.model.Greeting;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Greeting API", description = "Example greeting API")
public class GreetingController {
    private final GreetingUseCase greetingUseCase;

    public GreetingController(GreetingUseCase greetingUseCase) {
        this.greetingUseCase = greetingUseCase;
    }

    @Operation(summary = "Get personalized greeting")
    @GetMapping("/greeting")
    public GreetingResponse greeting(@RequestParam(defaultValue = "World") String name) {
        Greeting greeting = greetingUseCase.greet(name);
        return new GreetingResponse(greeting.getMessage());
    }
}
