package com.example.demo.modules.innovationcomments.adapters.web.controller;

import com.example.demo.modules.innovationcomments.adapters.web.dto.CreateInnovationCommentRequestDto;
import com.example.demo.modules.innovationcomments.adapters.web.dto.InnovationCommentResponseDto;
import com.example.demo.modules.innovationcomments.adapters.web.mapper.InnovationCommentMapper;
import com.example.demo.modules.innovationcomments.application.exception.CommentRejectedException;
import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import com.example.demo.modules.innovationcomments.domain.port.in.InnovationCommentUseCase;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationInfoJpaRepository;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationJpaRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        
        when(commentUseCase.createComment(100L, "John", "Doe", "test@example.com", "New comment"))
            .thenReturn(testComment);
        when(commentMapper.toResponseDto(testComment)).thenReturn(testResponseDto);

        // Act
        ResponseEntity<?> result = controller.createComment(request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(commentUseCase).createComment(100L, "John", "Doe", "test@example.com", "New comment");
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
        
        when(commentUseCase.createComment(100L, anyString(), anyString(), "test@example.com", "Spam comment"))
            .thenThrow(new CommentRejectedException("Comment rejected", "SPAM", "Internal spam detection"));

        // Act
        ResponseEntity<?> result = controller.createComment(request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void createComment_WithModificationJustification_ShouldCallCreateCommentWithAudit() {
        // Arrange
        CreateInnovationCommentRequestDto request = new CreateInnovationCommentRequestDto();
        request.setInnovationId(100L);
        request.setUserName("John");
        request.setUserLastname("Doe");
        request.setUserEmail("test@example.com");
        request.setComment("New comment");
        request.setModificationJustification("Initial creation");
        
        when(commentUseCase.createCommentWithAudit(100L, "John", "Doe", "test@example.com", 
                "New comment", "Initial creation"))
            .thenReturn(testComment);
        when(commentMapper.toResponseDto(testComment)).thenReturn(testResponseDto);

        // Act
        ResponseEntity<?> result = controller.createComment(request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        verify(commentUseCase).createCommentWithAudit(100L, "John", "Doe", 
                "test@example.com", "New comment", "Initial creation");
    }

    @Test
    void createComment_WhenIllegalArgumentException_ShouldReturnBadRequest() {
        // Arrange
        CreateInnovationCommentRequestDto request = new CreateInnovationCommentRequestDto();
        request.setInnovationId(100L);
        request.setUserName("John");
        request.setUserLastname("Doe");
        request.setUserEmail("test@example.com");
        request.setComment("Comment");
        
        when(commentUseCase.createComment(anyLong(), anyString(), anyString(), anyString(), anyString()))
            .thenThrow(new IllegalArgumentException("Invalid data"));

        // Act
        ResponseEntity<?> result = controller.createComment(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void getActiveCommentsByInnovationId_ShouldReturnComments() {
        // Arrange
        List<InnovationCatalogComment> comments = Arrays.asList(testComment);
        List<InnovationCommentResponseDto> dtos = Arrays.asList(testResponseDto);
        
        when(commentUseCase.getActiveCommentsByInnovationId(100L)).thenReturn(comments);
        when(commentMapper.toResponseDtoList(comments)).thenReturn(dtos);

        // Act
        ResponseEntity<List<InnovationCommentResponseDto>> result = 
                controller.getActiveCommentsByInnovationId(100L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void getActiveCommentsByInnovationId_WhenInvalidId_ShouldReturnBadRequest() {
        // Arrange
        when(commentUseCase.getActiveCommentsByInnovationId(anyLong()))
            .thenThrow(new IllegalArgumentException("Invalid ID"));

        // Act
        ResponseEntity<List<InnovationCommentResponseDto>> result = 
                controller.getActiveCommentsByInnovationId(999L);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void getActiveCommentsCount_ShouldReturnCount() {
        // Arrange
        when(commentUseCase.getActiveCommentsCount(100L)).thenReturn(5L);

        // Act
        ResponseEntity<Long> result = controller.getActiveCommentsCount(100L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(5L, result.getBody());
    }

    @Test
    void getActiveCommentsCount_WhenInvalidId_ShouldReturnBadRequest() {
        // Arrange
        when(commentUseCase.getActiveCommentsCount(anyLong()))
            .thenThrow(new IllegalArgumentException("Invalid ID"));

        // Act
        ResponseEntity<Long> result = controller.getActiveCommentsCount(999L);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void getActiveCommentsByUserEmail_ShouldReturnComments() {
        // Arrange
        List<InnovationCatalogComment> comments = Arrays.asList(testComment);
        List<InnovationCommentResponseDto> dtos = Arrays.asList(testResponseDto);
        
        when(commentUseCase.getActiveCommentsByUserEmail("test@example.com")).thenReturn(comments);
        when(commentMapper.toResponseDtoList(comments)).thenReturn(dtos);

        // Act
        ResponseEntity<List<InnovationCommentResponseDto>> result = 
                controller.getActiveCommentsByUserEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void getActiveCommentsByUserEmail_WhenInvalidEmail_ShouldReturnBadRequest() {
        // Arrange
        when(commentUseCase.getActiveCommentsByUserEmail(anyString()))
            .thenThrow(new IllegalArgumentException("Invalid email"));

        // Act
        ResponseEntity<List<InnovationCommentResponseDto>> result = 
                controller.getActiveCommentsByUserEmail("invalid");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void deactivateComment_WhenExists_ShouldReturnOk() {
        // Arrange
        when(commentUseCase.deactivateComment(1L)).thenReturn(true);

        // Act
        ResponseEntity<Void> result = controller.deactivateComment(1L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void deactivateComment_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        when(commentUseCase.deactivateComment(999L)).thenReturn(false);

        // Act
        ResponseEntity<Void> result = controller.deactivateComment(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void deactivateComment_WhenInvalidId_ShouldReturnBadRequest() {
        // Arrange
        when(commentUseCase.deactivateComment(anyLong()))
            .thenThrow(new IllegalArgumentException("Invalid ID"));

        // Act
        ResponseEntity<Void> result = controller.deactivateComment(999L);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void handleRuntimeException_WhenTableNotFound_ShouldReturnServiceUnavailable() {
        // Arrange
        RuntimeException e = new RuntimeException("table not found");

        // Act
        ResponseEntity<Void> result = controller.handleRuntimeException(e);

        // Assert
        assertEquals(HttpStatus.SERVICE_UNAVAILABLE, result.getStatusCode());
    }

    @Test
    void handleRuntimeException_WhenOtherError_ShouldReturnInternalServerError() {
        // Arrange
        RuntimeException e = new RuntimeException("Some other error");

        // Act
        ResponseEntity<Void> result = controller.handleRuntimeException(e);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void handleUnexpectedException_ShouldReturnInternalServerError() {
        // Arrange
        Exception e = new Exception("Unexpected error");

        // Act
        ResponseEntity<Void> result = controller.handleUnexpectedException(e);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void getAllComments_WhenIllegalArgumentException_ShouldReturnBadRequest() {
        // Arrange
        when(commentUseCase.getAllComments(anyInt(), anyInt()))
            .thenThrow(new IllegalArgumentException("Invalid limit"));

        // Act
        ResponseEntity<List<InnovationCommentResponseDto>> result = 
                controller.getAllComments(0, -1, null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void getAllComments_WithPhaseId_ShouldResolveInnovationNames() {
        // Arrange
        List<InnovationCatalogComment> comments = Arrays.asList(testComment);
        List<InnovationCommentResponseDto> dtos = Arrays.asList(testResponseDto);
        ProjectInnovationInfo info = new ProjectInnovationInfo();
        info.setTitle("Innovation Title");
        
        when(commentUseCase.getAllComments(0, null)).thenReturn(comments);
        when(commentMapper.toResponseDtoList(comments)).thenReturn(dtos);
        when(projectInnovationInfoRepository.findByProjectInnovationIdAndIdPhase(100L, 200L))
            .thenReturn(info);

        // Act
        ResponseEntity<List<InnovationCommentResponseDto>> result = 
                controller.getAllComments(0, null, 200L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
