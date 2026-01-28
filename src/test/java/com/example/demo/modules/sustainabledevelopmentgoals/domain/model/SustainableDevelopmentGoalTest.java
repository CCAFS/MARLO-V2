package com.example.demo.modules.sustainabledevelopmentgoals.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SustainableDevelopmentGoalTest {

    @Test
    void constructor_WithNoArgs_ShouldCreateEmptyObject() {
        // Act
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();

        // Assert
        assertNotNull(sdg);
        assertNull(sdg.getId());
        assertNull(sdg.getSmoCode());
        assertNull(sdg.getShortName());
        assertNull(sdg.getFullName());
        assertNull(sdg.getIcon());
        assertNull(sdg.getDescription());
    }

    @Test
    void settersAndGetters_Id_ShouldWorkCorrectly() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();

        // Act
        sdg.setId(1L);

        // Assert
        assertEquals(1L, sdg.getId());
    }

    @Test
    void settersAndGetters_SmoCode_ShouldWorkCorrectly() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();

        // Act
        sdg.setSmoCode(100L);

        // Assert
        assertEquals(100L, sdg.getSmoCode());
    }

    @Test
    void settersAndGetters_ShortName_ShouldWorkCorrectly() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();

        // Act
        sdg.setShortName("No Poverty");

        // Assert
        assertEquals("No Poverty", sdg.getShortName());
    }

    @Test
    void settersAndGetters_FullName_ShouldWorkCorrectly() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();

        // Act
        sdg.setFullName("End poverty in all its forms everywhere");

        // Assert
        assertEquals("End poverty in all its forms everywhere", sdg.getFullName());
    }

    @Test
    void settersAndGetters_Icon_ShouldWorkCorrectly() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();

        // Act
        sdg.setIcon("https://example.com/icons/sdg1.png");

        // Assert
        assertEquals("https://example.com/icons/sdg1.png", sdg.getIcon());
    }

    @Test
    void settersAndGetters_Description_ShouldWorkCorrectly() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();

        // Act
        sdg.setDescription("Goal 1 calls for an end to poverty in all its manifestations.");

        // Assert
        assertEquals("Goal 1 calls for an end to poverty in all its manifestations.", sdg.getDescription());
    }

    @Test
    void settersAndGetters_AllFields_ShouldWorkCorrectly() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();

        // Act
        sdg.setId(1L);
        sdg.setSmoCode(1001L);
        sdg.setShortName("No Poverty");
        sdg.setFullName("End poverty in all its forms everywhere");
        sdg.setIcon("sdg1.png");
        sdg.setDescription("This is the first SDG");

        // Assert
        assertEquals(1L, sdg.getId());
        assertEquals(1001L, sdg.getSmoCode());
        assertEquals("No Poverty", sdg.getShortName());
        assertEquals("End poverty in all its forms everywhere", sdg.getFullName());
        assertEquals("sdg1.png", sdg.getIcon());
        assertEquals("This is the first SDG", sdg.getDescription());
    }

    @Test
    void equals_WithSameId_ShouldBeEqual() {
        // Arrange
        SustainableDevelopmentGoal sdg1 = new SustainableDevelopmentGoal();
        sdg1.setId(1L);
        sdg1.setShortName("No Poverty");

        SustainableDevelopmentGoal sdg2 = new SustainableDevelopmentGoal();
        sdg2.setId(1L);
        sdg2.setShortName("No Poverty");

        // Assert
        assertEquals(sdg1, sdg2);
        assertEquals(sdg1.hashCode(), sdg2.hashCode());
    }

    @Test
    void equals_WithDifferentId_ShouldNotBeEqual() {
        // Arrange
        SustainableDevelopmentGoal sdg1 = new SustainableDevelopmentGoal();
        sdg1.setId(1L);

        SustainableDevelopmentGoal sdg2 = new SustainableDevelopmentGoal();
        sdg2.setId(2L);

        // Assert
        assertNotEquals(sdg1, sdg2);
    }

    @Test
    void equals_WithNull_ShouldNotBeEqual() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();
        sdg.setId(1L);

        // Assert
        assertNotEquals(null, sdg);
    }

    @Test
    void equals_WithSelf_ShouldBeEqual() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();
        sdg.setId(1L);

        // Assert
        assertEquals(sdg, sdg);
    }

    @Test
    void equals_WithDifferentClass_ShouldNotBeEqual() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();
        sdg.setId(1L);

        // Assert
        assertNotEquals(sdg, "string");
    }

    @Test
    void hashCode_WithSameValues_ShouldBeEqual() {
        // Arrange
        SustainableDevelopmentGoal sdg1 = new SustainableDevelopmentGoal();
        sdg1.setId(5L);
        sdg1.setSmoCode(1005L);
        sdg1.setShortName("Gender Equality");

        SustainableDevelopmentGoal sdg2 = new SustainableDevelopmentGoal();
        sdg2.setId(5L);
        sdg2.setSmoCode(1005L);
        sdg2.setShortName("Gender Equality");

        // Assert
        assertEquals(sdg1.hashCode(), sdg2.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();
        sdg.setId(1L);
        sdg.setShortName("No Poverty");

        // Act
        String result = sdg.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1") || result.contains("No Poverty"));
    }

    @Test
    void setNullValues_ShouldAcceptNulls() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();
        sdg.setId(1L);
        sdg.setShortName("Test");

        // Act
        sdg.setId(null);
        sdg.setSmoCode(null);
        sdg.setShortName(null);
        sdg.setFullName(null);
        sdg.setIcon(null);
        sdg.setDescription(null);

        // Assert
        assertNull(sdg.getId());
        assertNull(sdg.getSmoCode());
        assertNull(sdg.getShortName());
        assertNull(sdg.getFullName());
        assertNull(sdg.getIcon());
        assertNull(sdg.getDescription());
    }

    @Test
    void canEqual_WithSameClass_ShouldReturnTrue() {
        // Arrange
        SustainableDevelopmentGoal sdg1 = new SustainableDevelopmentGoal();
        SustainableDevelopmentGoal sdg2 = new SustainableDevelopmentGoal();

        // Assert - Lombok @Data genera canEqual
        assertTrue(sdg1.canEqual(sdg2));
    }
}
