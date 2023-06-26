package com.musala.drones.mappers;

import com.musala.drones.model.dto.MedicationDTO;
import com.musala.drones.model.entity.MedicationEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicationMapper {

    public MedicationDTO toDto(MedicationEntity entity) {
        MedicationDTO dto = new MedicationDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setWeight(entity.getWeight());
        dto.setCode(entity.getCode());
        dto.setImagePath(entity.getImagePath());
        return dto;
    }

    public MedicationEntity toEntity(MedicationDTO dto) {
        return MedicationEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .weight(dto.getWeight())
                .code(dto.getCode())
                .imagePath(dto.getImagePath())
                .build();
    }

    public List<MedicationDTO> toDtoList(List<MedicationEntity> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<MedicationEntity> toEntityList(List<MedicationDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
