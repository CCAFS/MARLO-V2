package com.example.demo.modules.greeting.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GreetingTest {
    @Test
    void constructorStoresMessage() {
        Greeting greeting = new Greeting("Hello");
        assertEquals("Hello", greeting.getMessage());
    }

    @Test
    void constructorRejectsNullMessage() {
        assertThrows(NullPointerException.class, () -> new Greeting(null));
    }
}
