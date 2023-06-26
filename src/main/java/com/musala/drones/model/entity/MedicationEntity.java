package com.musala.drones.model.entity;


import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

@Data
@Table("medications")
@Builder(toBuilder = true)
public class MedicationEntity {

    @Id
    @Column("id")
    private Long id;

    @NonNull
    @Column("name")
    @Pattern(regexp ="^[a-zA-Z0-9-_]+$")
    private String name;

    @NonNull
    @Column("weight")
    private Double weight;

    @NonNull
    @Column("code")
    @Pattern(regexp = "^[A-Z0-9_]+$")
    private String code;

    @Column("image_path")
    private String imagePath;
}
