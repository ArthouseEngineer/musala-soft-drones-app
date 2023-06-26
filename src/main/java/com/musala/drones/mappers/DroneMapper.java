package com.musala.drones.mappers;

import com.musala.drones.model.dto.DroneDTO;
import com.musala.drones.model.entity.DroneEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DroneMapper {

    private final MedicationMapper medicationMapper;


    public DroneDTO toDto(DroneEntity entity) {
        DroneDTO dto = new DroneDTO();
        dto.setId(entity.getId());
        dto.setSerialNumber(entity.getSerialNumber());
        dto.setModel(entity.getModel());
        dto.setCurrentWorkload(entity.getCurrentWorkload());
        dto.setWeightLimit(entity.getWeightLimit());
        dto.setBatteryCapacity(entity.getBatteryCapacity());
        dto.setState(entity.getState());
        if (CollectionUtils.isEmpty(entity.getMedications())) {
            dto.setLoadedMedications(Collections.emptyList());
            return dto;
        }
        dto.setLoadedMedications(entity.getMedications()
                .stream()
                .map(medicationMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }

    public DroneEntity toEntity(DroneDTO dto) {
        return DroneEntity.builder()
                .id(dto.getId())
                .serialNumber(dto.getSerialNumber())
                .model(dto.getModel())
                .currentWorkload(dto.getCurrentWorkload())
                .weightLimit(dto.getWeightLimit())
                .batteryCapacity(dto.getBatteryCapacity())
                .medications(medicationMapper.toEntityList(dto.getLoadedMedications()))
                .state(dto.getState())
                .build();
    }
}