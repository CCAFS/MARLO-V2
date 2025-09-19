package com.example.demo.modules.farewell.application.service;

import com.example.demo.modules.farewell.application.port.inbound.FarewellUseCase;
import com.example.demo.modules.farewell.domain.model.Farewell;
import com.example.demo.sharedkernel.application.PersonalizedMessageGenerator;
import org.springframework.stereotype.Service;

@Service
public class FarewellService implements FarewellUseCase {
    private final PersonalizedMessageGenerator messageGenerator;

    public FarewellService(PersonalizedMessageGenerator messageGenerator) {
        this.messageGenerator = messageGenerator;
    }

    @Override
    public Farewell sayGoodbye(String name) {
        String message = messageGenerator.generate("Goodbye, %s!", name);
        return new Farewell(message);
    }
}
