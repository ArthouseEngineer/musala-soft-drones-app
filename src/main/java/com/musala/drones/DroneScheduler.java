package com.musala.drones;

import com.musala.drones.model.entity.DroneEntity;
import com.musala.drones.model.enums.DroneState;
import com.musala.drones.repository.DroneRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;

import java.util.Collections;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class DroneScheduler {

    private final DroneRepository droneRepository;

    @Scheduled(fixedDelayString = "${musala.drones-scheduler-delay-ms}")
    @PostConstruct
    public void run() {
        startCharging();
        checkDroneBattery();
        startReturning();
        endDelivery();
        startDelivery();
    }


    private Flux<DroneEntity> transitionState(DroneState prev, DroneState next) {
        return droneRepository.findAllByState(prev)
                .flatMap(drone -> {
                    drone.setState(next);
                    return droneRepository.save(drone);
                });
    }

    private void startDelivery() {
        transitionState(DroneState.LOADED, DroneState.DELIVERING);
    }

    void endDelivery() {
        transitionState(DroneState.DELIVERING, DroneState.DELIVERED)
                .flatMap(drone -> {
                    drone.setIsDelivered(true);
                    drone.setMedications(Collections.emptyList());
                    return droneRepository.save(drone);
                })
                .then()
                .subscribe();
    }

    private void startReturning() {
        transitionState(DroneState.DELIVERED, DroneState.RETURNING);
    }

    private void checkDroneBattery() {
        droneRepository.findAllByState(DroneState.RETURNING)
                .flatMap(drone -> {
                    int batteryCapacity = drone.getBatteryCapacity() - 25;
                    drone.setBatteryCapacity(batteryCapacity);

                    if (batteryCapacity < 25) {
                        drone.setBatteryCapacity(0);
                        drone.setState(DroneState.IDLE);
                    } else {
                        drone.setState(DroneState.LOADING);
                    }

                    return droneRepository.save(drone);
                })
                .subscribe();
    }

    public void startCharging() {
        droneRepository.findAllByState(DroneState.IDLE)
                .flatMap(drone -> {
                    int batteryCapacity = drone.getBatteryCapacity() + 25;
                    int newBatteryCapacity = Math.min(batteryCapacity, 100);
                    DroneState newState = newBatteryCapacity == 100 ? DroneState.LOADING : DroneState.IDLE;
                    drone.setBatteryCapacity(newBatteryCapacity);
                    drone.setMedications(Collections.emptyList());
                    drone.setState(newState);
                    return droneRepository.save(drone);
                })
                .subscribe(drone -> log.debug("Drone {}, updated successfully", drone.getId()),
                        error -> log.error("Error updating drones: {}", error.getMessage()));
    }
}

