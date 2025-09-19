package com.example.demo.modules.greeting.application.port.inbound;

import com.example.demo.modules.greeting.domain.model.Greeting;

public interface GreetingUseCase {
    Greeting greet(String name);
}
