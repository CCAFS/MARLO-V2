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

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        User u1 = new User();
        User u2 = new User();
        u1.setId(1L);
        u2.setId(1L);

        // Act & Assert
        assertEquals(u1, u2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        User u1 = new User();
        User u2 = new User();
        u1.setId(1L);
        u2.setId(2L);

        // Act & Assert
        assertNotEquals(u1, u2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(user, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(user, "not a user");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(user, user);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String username = "johndoe";

        User u1 = new User(id, firstName, lastName, email, username);
        User u2 = new User(id, firstName, lastName, email, username);

        // Act & Assert
        assertEquals(u1, u2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        User u1 = new User();
        User u2 = new User();
        u1.setId(1L);
        u1.setEmail("email1@example.com");
        u2.setId(1L);
        u2.setEmail("email2@example.com");

        // Act & Assert
        assertNotEquals(u1, u2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        User u1 = new User();
        User u2 = new User();
        u1.setId(1L);
        u1.setEmail("email@example.com");
        u2.setId(1L);
        u2.setEmail("email@example.com");

        // Act & Assert
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        User u1 = new User();
        User u2 = new User();
        u1.setId(1L);
        u1.setEmail("email1@example.com");
        u2.setId(2L);
        u2.setEmail("email2@example.com");

        // Act & Assert
        assertNotEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert
        assertDoesNotThrow(() -> user.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = user.toString();

        // Assert
        assertTrue(toString.contains("User"));
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        User u1 = new User();
        User u2 = new User();
        u1.setId(null);
        u1.setEmail(null);
        u2.setId(null);
        u2.setEmail(null);

        // Act & Assert
        assertEquals(u1, u2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        User u1 = new User();
        User u2 = new User();
        u1.setId(1L);
        u1.setEmail("email@example.com");
        u2.setId(1L);
        u2.setEmail(null);

        // Act & Assert
        assertNotEquals(u1, u2);
    }
}
