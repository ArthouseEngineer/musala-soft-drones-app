package com.musala.drones.model.entity;

import com.musala.drones.model.enums.DroneModel;
import com.musala.drones.model.enums.DroneState;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;


@Data
@Table("drones")
@Builder(toBuilder = true)
public class DroneEntity {

    @Id
    @Column("id")
    private Long id;

    @NonNull
    @Column("serial_number")
    @Size(max = 100)
    private String serialNumber;

    @NonNull
    @Column("model")
    private DroneModel model;

    @NonNull
    @Max(500)
    @Column("current_workload")
    private double currentWorkload;

    @NonNull
    @Max(500)
    @Column("weight_limit")
    private double weightLimit;

    @NonNull
    @Column("battery_capacity")
    private int batteryCapacity;

    @NonNull
    @Column("state")
    private DroneState state;

    @NonNull
    @Column("is_delivered")
    private Boolean isDelivered;

    @Nullable
    private List<MedicationEntity> medications;
}