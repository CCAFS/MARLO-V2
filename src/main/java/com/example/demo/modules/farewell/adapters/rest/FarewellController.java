package com.example.demo.modules.farewell.adapters.rest;

import com.example.demo.modules.farewell.application.port.inbound.FarewellUseCase;
import com.example.demo.modules.farewell.domain.model.Farewell;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Farewell API", description = "Example farewell API")
public class FarewellController {
    private final FarewellUseCase farewellUseCase;

    public FarewellController(FarewellUseCase farewellUseCase) {
        this.farewellUseCase = farewellUseCase;
    }

    @Operation(summary = "Get personalized farewell")
    @GetMapping("/farewell")
    public FarewellResponse farewell(@RequestParam(defaultValue = "Friend") String name) {
        Farewell farewell = farewellUseCase.sayGoodbye(name);
        return new FarewellResponse(farewell.getMessage());
    }
}
