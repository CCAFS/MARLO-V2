package com.example.demo.modules.actors.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO used for control list responses (value/label pairs)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Actor control list item")
public class ActorControlListResponse {

    @Schema(description = "Identifier used as the value in control lists", example = "1")
    private Long value;

    @Schema(description = "Label displayed to users", example = "Farmers")
    private String label;

    @Schema(description = "Optional PRMS name equivalent", example = "Agricultural Producer")
    private String prmsNameEquivalent;
}
