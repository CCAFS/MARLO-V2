package com.example.demo.modules.innovationcomments.adapters.web.controller;

import com.example.demo.modules.innovationcomments.adapters.web.dto.CreateInnovationCommentRequestDto;
import com.example.demo.modules.innovationcomments.adapters.web.dto.InnovationCommentResponseDto;
import com.example.demo.modules.innovationcomments.adapters.web.mapper.InnovationCommentMapper;
import com.example.demo.modules.innovationcomments.application.exception.CommentRejectedException;
import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import com.example.demo.modules.innovationcomments.domain.port.in.InnovationCommentUseCase;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationInfoJpaRepository;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationJpaRepository;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnovationCommentControllerTest {

    @Mock
    private InnovationCommentUseCase commentUseCase;

    @Mock
    private InnovationCommentMapper commentMapper;

    @Mock
    private ProjectInnovationInfoJpaRepository projectInnovationInfoRepository;

    @Mock
    private ProjectInnovationJpaRepository projectInnovationRepository;

    @InjectMocks
    private InnovationCommentController controller;

    private InnovationCatalogComment testComment;
    private InnovationCommentResponseDto testResponseDto;

    @BeforeEach
    void setUp() {
        testComment = new InnovationCatalogComment();
        testComment.setId(1L);
        testComment.setInnovationId(100L);
        testComment.setComment("Test comment");
        testComment.setIsActive(true);

        testResponseDto = new InnovationCommentResponseDto();
        testResponseDto.setId(1L);
        testResponseDto.setInnovationId(100L);
        testResponseDto.setComment("Test comment");
    }

    @Test
    void getAllComments_ShouldReturnList() {
        // Arrange
        List<InnovationCatalogComment> comments = Arrays.asList(testComment);
        List<InnovationCommentResponseDto> dtos = Arrays.asList(testResponseDto);
        
        when(commentUseCase.getAllComments(0, null)).thenReturn(comments);
        when(commentMapper.toResponseDtoList(comments)).thenReturn(dtos);

        // Act
        ResponseEntity<List<InnovationCommentResponseDto>> result = controller.getAllComments(0, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        verify(commentUseCase).getAllComments(0, null);
    }

    @Test
    void getAllComments_WithLimit_ShouldApplyLimit() {
        // Arrange
        List<InnovationCatalogComment> comments = Arrays.asList(testComment);
        List<InnovationCommentResponseDto> dtos = Arrays.asList(testResponseDto);
        
        when(commentUseCase.getAllComments(0, 10)).thenReturn(comments);
        when(commentMapper.toResponseDtoList(comments)).thenReturn(dtos);

        // Act
        ResponseEntity<List<InnovationCommentResponseDto>> result = controller.getAllComments(0, 10, null);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(commentUseCase).getAllComments(0, 10);
    }

    @Test
    void getAllComments_WhenEmpty_ShouldReturnEmptyList() {
        // Arrange
        when(commentUseCase.getAllComments(0, null)).thenReturn(Collections.emptyList());
        when(commentMapper.toResponseDtoList(any())).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<InnovationCommentResponseDto>> result = controller.getAllComments(0, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
    }

    @Test
    void createComment_WhenValid_ShouldReturnCreated() {
        // Arrange
        CreateInnovationCommentRequestDto request = new CreateInnovationCommentRequestDto();
        request.setInnovationId(100L);
        request.setUserName("John");
        request.setUserLastname("Doe");
        request.setUserEmail("test@example.com");
        request.setComment("New comment");
        
        when(commentUseCase.createComment(eq(100L), eq("John"), eq("Doe"), eq("test@example.com"), eq("New comment")))
            .thenReturn(testComment);
        when(commentMapper.toResponseDto(testComment)).thenReturn(testResponseDto);

        // Act
        ResponseEntity<?> result = controller.createComment(request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(commentUseCase).createComment(eq(100L), eq("John"), eq("Doe"), eq("test@example.com"), eq("New comment"));
    }

    @Test
    void createComment_WhenRejected_ShouldReturnBadRequest() {
        // Arrange
        CreateInnovationCommentRequestDto request = new CreateInnovationCommentRequestDto();
        request.setInnovationId(100L);
        request.setUserName("John");
        request.setUserLastname("Doe");
        request.setUserEmail("test@example.com");
        request.setComment("Spam comment");
        
        when(commentUseCase.createComment(eq(100L), anyString(), anyString(), eq("test@example.com"), eq("Spam comment")))
            .thenThrow(new CommentRejectedException("Comment rejected", "SPAM", "Internal spam detection"));

        // Act
        ResponseEntity<?> result = controller.createComment(request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}
