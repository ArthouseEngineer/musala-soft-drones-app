package com.musala.drones.repository;

import com.musala.drones.model.entity.DroneEntity;
import com.musala.drones.model.entity.MedicationEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MedicationRepository extends R2dbcRepository<MedicationEntity, Long> {
    @Query("""
            SELECT m.* FROM medications m 
            JOIN drone_medications dm ON dm.medication_id = m.id 
            WHERE dm.drone_id = :droneId
            """)
    Flux<MedicationEntity> findMedicationsByDroneId(@Param("droneId") Long droneId);
}
