package com.example.demo.modules.greeting.application.service;

import com.example.demo.modules.greeting.application.port.inbound.GreetingUseCase;
import com.example.demo.modules.greeting.domain.model.Greeting;
import com.example.demo.sharedkernel.application.PersonalizedMessageGenerator;
import org.springframework.stereotype.Service;

@Service
public class GreetingService implements GreetingUseCase {
    private final PersonalizedMessageGenerator messageGenerator;

    public GreetingService(PersonalizedMessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    @Override
    public Greeting greet(String name) {
        String message = messageGenerator.generate("Hello, %s!", name);
        return new Greeting(message);
    }
}
