package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void constructor_NoArgsConstructor_ShouldCreateEmpty() {
        // Act
        User newUser = new User();

        // Assert
        assertNull(newUser.getId());
        assertNull(newUser.getFirstName());
        assertNull(newUser.getLastName());
        assertNull(newUser.getEmail());
        assertNull(newUser.getUsername());
    }

    @Test
    void constructor_AllArgsConstructor_ShouldSetAllFields() {
        // Arrange
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String username = "johndoe";

        // Act
        User newUser = new User(id, firstName, lastName, email, username);

        // Assert
        assertEquals(id, newUser.getId());
        assertEquals(firstName, newUser.getFirstName());
        assertEquals(lastName, newUser.getLastName());
        assertEquals(email, newUser.getEmail());
        assertEquals(username, newUser.getUsername());
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        String firstName = "Jane";
        String lastName = "Smith";
        String email = "jane.smith@example.com";
        String username = "janesmith";

        // Act
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUsername(username);

        // Assert
        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(username, user.getUsername());
    }
}
