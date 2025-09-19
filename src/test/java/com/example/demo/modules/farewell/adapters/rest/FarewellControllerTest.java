package com.example.demo.modules.farewell.adapters.rest;

import com.example.demo.modules.farewell.application.port.inbound.FarewellUseCase;
import com.example.demo.modules.farewell.domain.model.Farewell;
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
class FarewellControllerTest {
    @SpringBootConfiguration
    @EnableAutoConfiguration
    static class TestConfig {
        @Bean
        FarewellController farewellController(FarewellUseCase farewellUseCase) {
            return new FarewellController(farewellUseCase);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FarewellUseCase farewellUseCase;

    @Test
    void farewellEndpoint_returnsFarewell() throws Exception {
        when(farewellUseCase.sayGoodbye("Test")).thenReturn(new Farewell("Goodbye, Test!"));

        mockMvc.perform(get("/farewell").param("name", "Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Goodbye, Test!"));
    }
}
