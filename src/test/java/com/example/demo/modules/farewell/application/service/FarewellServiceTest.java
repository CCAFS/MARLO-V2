package com.example.demo.modules.farewell.application.service;

import com.example.demo.modules.farewell.domain.model.Farewell;
import com.example.demo.sharedkernel.application.PersonalizedMessageGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FarewellServiceTest {
    @Test
    void sayGoodbye_returnsCorrectMessage() {
        FarewellService service = new FarewellService(new PersonalizedMessageGenerator());
        Farewell farewell = service.sayGoodbye("Marlo");
        assertEquals("Goodbye, Marlo!", farewell.getMessage());
    }
}
