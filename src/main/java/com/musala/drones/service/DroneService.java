package com.musala.drones.service;

import com.musala.drones.mappers.DroneMapper;
import com.musala.drones.mappers.MedicationMapper;
import com.musala.drones.model.dto.DroneDTO;
import com.musala.drones.model.dto.MedicationDTO;
import com.musala.drones.model.entity.DroneEntity;
import com.musala.drones.model.enums.DroneState;
import com.musala.drones.repository.DroneRepository;
import com.musala.drones.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneService {

    private final DroneRepository droneRepository;

    private final MedicationRepository medicationRepository;

    private final DroneMapper droneMapper;

    private final MedicationMapper medicationMapper;


    public Mono<DroneDTO> register(DroneDTO drone) {
        return droneRepository.save(droneMapper.toEntity(drone))
                .map(droneMapper::toDto);
    }


    public Mono<DroneDTO> load(Long droneId, List<Long> medicationIds) {
        return droneRepository.findById(droneId)
                .flatMap(droneEntity -> {
                    if (droneEntity.getBatteryCapacity() < 25 || droneEntity.getState() != DroneState.IDLE) {
                        return Mono.error(new IllegalStateException("Drone cannot be loaded"));
                    }

                    return medicationRepository.findAllById(medicationIds)
                            .collectList()
                            .flatMap(medications -> {
                                int totalWeight = medications.stream().mapToInt(medication -> (int) Math.round(medication.getWeight())).sum();
                                if (droneEntity.getCurrentWorkload() + totalWeight > droneEntity.getWeightLimit()) {
                                    return Mono.error(new IllegalStateException("Drone cannot carry this weight"));
                                }
                                return droneRepository.load(droneEntity.getId(), medicationIds, medicationRepository)
                                        .flatMap(drone -> medicationRepository.findMedicationsByDroneId(drone.getId())
                                                .collectList()
                                                .map(medicationsLoaded -> {
                                                    drone.setMedications(medicationsLoaded);
                                                    return drone;
                                                })
                                                .map(droneMapper::toDto));
                            });
                });
    }


    public Flux<MedicationDTO> getLoaded(Long droneId) {
        return droneRepository.findById(droneId)
                .flatMapMany(droneEntity -> medicationRepository.findMedicationsByDroneId(droneId)
                        .map(medicationMapper::toDto)
                )
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Drone not found")));
    }

    public Flux<DroneDTO> getAvailable() {

        return droneRepository.findAllByState(DroneState.IDLE)
                .flatMap(drone -> medicationRepository.findMedicationsByDroneId(drone.getId())
                        .collectList()
                        .map(medications -> {
                            drone.setMedications(medications);
                            return drone;
                        })
                        .map(droneMapper::toDto))
                .sort(Comparator.comparing(DroneDTO::getId));
    }


    public Mono<Integer> checkBatteryLevel(Long droneId) {
        return droneRepository.findById(droneId)
                .map(DroneEntity::getBatteryCapacity);
    }
}
