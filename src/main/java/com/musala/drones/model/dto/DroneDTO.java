package com.musala.drones.model.dto;

import com.musala.drones.model.enums.DroneModel;
import com.musala.drones.model.enums.DroneState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A drone object")
public class DroneDTO {

    private Long id;

    @NotBlank
    @Size(max = 100)
    @Schema(description = "The serial number of the drone", example = "DJISpark-1234567890")
    private String serialNumber;

    @NotNull
    @Schema(description = "The model of the drone", example = "Middleweight")
    private DroneModel model;

    @NotNull
    @Max(500)
    @Schema(description = "The weight limit of the drone in grams", example = "400")
    private double weightLimit;

    @NotNull
    @Max(500)
    @Schema(description = "Current workload of the drone", example = "400")
    private double currentWorkload;

    @Schema(description = "List of medication for drone", example = "400")
    private List<MedicationDTO> loadedMedications;

    @NotNull
    @Schema(description = "The battery capacity of the drone in percentage", example = "80")
    private int batteryCapacity;

    @NotNull
    @Schema(description = "The current state of the drone", example = "IDLE")
    private DroneState state;
}
