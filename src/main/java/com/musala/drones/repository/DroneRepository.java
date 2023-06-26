package com.musala.drones.repository;

import com.musala.drones.model.entity.DroneEntity;
import com.musala.drones.model.enums.DroneState;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Repository
public interface DroneRepository extends R2dbcRepository<DroneEntity, Long> {

    @Query("""
            SELECT d.*
            FROM drones d
            WHERE d.state = :state
            """)
    Flux<DroneEntity> findAllByState(@Param("state") DroneState state);


    @Modifying
    @Query("""
            INSERT INTO drone_medications (drone_id, medication_id) VALUES (:droneId, :medicationId)
            """)
    Mono<Void> addMedication(Long droneId, Long medicationId);

    @Query("""
            SELECT DISTINCT d.*, m.* FROM drones d 
            JOIN drone_medications dm ON d.id = dm.drone_id 
            JOIN medications m ON dm.medication_id = m.id WHERE d.id = :droneId
            """)
    Mono<DroneEntity> findDroneById(@Param("droneId") Long id);

    default Mono<DroneEntity> load(Long droneId, List<Long> medicationIds, MedicationRepository medicationRepository) {
        return Flux.fromIterable(medicationIds)
                .flatMap(medicationId -> addMedication(droneId, medicationId))
                .then(findDroneById(droneId))
                .flatMap(droneEntity ->
                        medicationRepository.findMedicationsByDroneId(droneId)
                                .collectList()
                                .map(medications -> {
                                    droneEntity.setMedications(medications);
                                    return droneEntity;
                                }))
                        .then(findDroneById(droneId));
    }
}
