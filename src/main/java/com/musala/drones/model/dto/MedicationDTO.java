package com.musala.drones.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a medication")
public class MedicationDTO {

    @Schema(description = "The medication ID")
    private Long id;

    @Schema(description = "The medication name. Must consist of alphanumeric characters, hyphens, and underscores.")
    @NotBlank(message = "Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9-_]+$", message = "Name must consist of alphanumeric characters, hyphens, and underscores")
    private String name;

    @Schema(description = "The medication weight")
    @NotNull(message = "Weight cannot be null")
    private double weight;

    @Schema(description = "The medication code. Must consist of uppercase alphanumeric characters and underscores.")
    @NotBlank(message = "Code cannot be blank")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code must consist of uppercase alphanumeric characters and underscores")
    private String code;

    @Schema(description = "The URL of the medication image")
    private String imagePath;

}
