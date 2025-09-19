package com.example.demo.modules.greeting.application.service;

import com.example.demo.modules.greeting.domain.model.Greeting;
import com.example.demo.sharedkernel.application.PersonalizedMessageGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GreetingServiceTest {
    @Test
    void greet_returnsCorrectMessage() {
        GreetingService service = new GreetingService(new PersonalizedMessageGenerator());
        Greeting greeting = service.greet("Marlo");
        assertEquals("Hello, Marlo!", greeting.getMessage());
    }
}
