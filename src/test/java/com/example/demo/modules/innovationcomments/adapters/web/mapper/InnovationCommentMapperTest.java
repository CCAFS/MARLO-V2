package com.example.demo.modules.innovationcomments.adapters.web.mapper;

import com.example.demo.modules.innovationcomments.adapters.web.dto.CreateInnovationCommentRequestDto;
import com.example.demo.modules.innovationcomments.adapters.web.dto.InnovationCommentResponseDto;
import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InnovationCommentMapperTest {

    @InjectMocks
    private InnovationCommentMapper mapper;

    private InnovationCatalogComment testComment;
    private CreateInnovationCommentRequestDto testRequestDto;

    @BeforeEach
    void setUp() {
        testComment = new InnovationCatalogComment();
        testComment.setId(1L);
        testComment.setInnovationId(100L);
        testComment.setUserName("John");
        testComment.setUserLastname("Doe");
        testComment.setUserEmail("john@example.com");
        testComment.setComment("Test comment");
        testComment.setIsActive(true);
        testComment.setActiveSince(LocalDateTime.now());

        testRequestDto = new CreateInnovationCommentRequestDto();
        testRequestDto.setInnovationId(100L);
        testRequestDto.setUserName("John");
        testRequestDto.setUserLastname("Doe");
        testRequestDto.setUserEmail("john@example.com");
        testRequestDto.setComment("Test comment");
    }

    @Test
    void toResponseDto_WhenCommentNotNull_ShouldReturnDto() {
        // Act
        InnovationCommentResponseDto result = mapper.toResponseDto(testComment);

        // Assert
        assertNotNull(result);
        assertEquals(testComment.getId(), result.getId());
        assertEquals(testComment.getInnovationId(), result.getInnovationId());
        assertEquals(testComment.getUserName(), result.getUserName());
        assertEquals(testComment.getUserLastname(), result.getUserLastname());
        assertEquals(testComment.getComment(), result.getComment());
    }

    @Test
    void toResponseDto_WhenCommentNull_ShouldReturnNull() {
        // Act
        InnovationCommentResponseDto result = mapper.toResponseDto(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toResponseDtoList_WhenListNotNull_ShouldReturnList() {
        // Arrange
        List<InnovationCatalogComment> comments = Arrays.asList(testComment);

        // Act
        List<InnovationCommentResponseDto> result = mapper.toResponseDtoList(comments);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testComment.getId(), result.get(0).getId());
    }

    @Test
    void toResponseDtoList_WhenListNull_ShouldReturnEmptyList() {
        // Act
        List<InnovationCommentResponseDto> result = mapper.toResponseDtoList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toResponseDtoList_WhenListEmpty_ShouldReturnEmptyList() {
        // Arrange
        List<InnovationCatalogComment> comments = Collections.emptyList();

        // Act
        List<InnovationCommentResponseDto> result = mapper.toResponseDtoList(comments);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toEntity_WhenRequestNotNull_ShouldReturnEntity() {
        // Act
        InnovationCatalogComment result = mapper.toEntity(testRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(testRequestDto.getInnovationId(), result.getInnovationId());
        assertEquals(testRequestDto.getUserName(), result.getUserName());
        assertEquals(testRequestDto.getUserLastname(), result.getUserLastname());
        assertEquals(testRequestDto.getComment(), result.getComment());
    }

    @Test
    void toEntity_WhenRequestNull_ShouldReturnNull() {
        // Act
        InnovationCatalogComment result = mapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toEntity_WithModificationJustification_ShouldSetJustification() {
        // Arrange
        testRequestDto.setModificationJustification("Test justification");

        // Act
        InnovationCatalogComment result = mapper.toEntity(testRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test justification", result.getModificationJustification());
    }
}
