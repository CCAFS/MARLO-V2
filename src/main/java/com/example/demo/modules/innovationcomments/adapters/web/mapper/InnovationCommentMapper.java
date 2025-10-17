package com.example.demo.modules.innovationcomments.adapters.web.mapper;

import com.example.demo.modules.innovationcomments.adapters.web.dto.CreateInnovationCommentRequestDto;
import com.example.demo.modules.innovationcomments.adapters.web.dto.InnovationCommentResponseDto;
import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for converting between DTOs and domain entities
 * Handles the conversion logic for Innovation Comments
 */
@Component
public class InnovationCommentMapper {
    
    /**
     * Convert domain entity to response DTO
     * @param comment The domain entity
     * @return Response DTO
     */
    public InnovationCommentResponseDto toResponseDto(InnovationCatalogComment comment) {
        if (comment == null) {
            return null;
        }
        
        return new InnovationCommentResponseDto(
            comment.getId(),
            comment.getInnovationId(),
            comment.getUserName(),
            comment.getUserLastname(),
            comment.getUserEmail(),
            comment.getComment(),
            comment.getIsActive(),
            comment.getActiveSince(),
            comment.getModificationJustification()
        );
    }
    
    /**
     * Convert list of domain entities to list of response DTOs
     * @param comments List of domain entities
     * @return List of response DTOs
     */
    public List<InnovationCommentResponseDto> toResponseDtoList(List<InnovationCatalogComment> comments) {
        if (comments == null) {
            return null;
        }
        
        return comments.stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Convert create request DTO to domain entity
     * @param requestDto The create request DTO
     * @return Domain entity
     */
    public InnovationCatalogComment toEntity(CreateInnovationCommentRequestDto requestDto) {
        if (requestDto == null) {
            return null;
        }
        
        InnovationCatalogComment comment = new InnovationCatalogComment(
            requestDto.getInnovationId(),
            requestDto.getUserName(),
            requestDto.getUserLastname(),
            requestDto.getUserEmail(),
            requestDto.getComment()
        );
        
        if (requestDto.getModificationJustification() != null) {
            comment.setModificationJustification(requestDto.getModificationJustification());
        }
        
        return comment;
    }
}