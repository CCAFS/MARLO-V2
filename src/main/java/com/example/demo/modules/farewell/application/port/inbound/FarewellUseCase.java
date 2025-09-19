package com.example.demo.modules.farewell.application.port.inbound;

import com.example.demo.modules.farewell.domain.model.Farewell;

public interface FarewellUseCase {
    Farewell sayGoodbye(String name);
}
