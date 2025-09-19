package com.example.demo.modules.farewell.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FarewellTest {
    @Test
    void constructorStoresMessage() {
        Farewell farewell = new Farewell("Goodbye");
        assertEquals("Goodbye", farewell.getMessage());
    }

    @Test
    void constructorRejectsNullMessage() {
        assertThrows(NullPointerException.class, () -> new Farewell(null));
    }
}
