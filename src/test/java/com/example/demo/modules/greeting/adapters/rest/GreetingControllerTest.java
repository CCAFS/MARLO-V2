package com.example.demo.modules.greeting.adapters.rest;

import com.example.demo.modules.greeting.application.port.inbound.GreetingUseCase;
import com.example.demo.modules.greeting.domain.model.Greeting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class GreetingControllerTest {
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestConfig {
        @Bean
        GreetingController greetingController(GreetingUseCase greetingUseCase) {
            return new GreetingController(greetingUseCase);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GreetingUseCase greetingUseCase;

    @Test
    void greetingEndpoint_returnsGreeting() throws Exception {
        when(greetingUseCase.greet("Test")).thenReturn(new Greeting("Hello, Test!"));

        mockMvc.perform(get("/greeting").param("name", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Hello, Test!"));
    }
}
