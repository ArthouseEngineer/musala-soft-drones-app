package com.musala.drones.handlers;

import com.musala.drones.model.dto.DroneDTO;
import com.musala.drones.model.dto.DroneLoadRQ;
import com.musala.drones.model.dto.MedicationDTO;
import com.musala.drones.service.DroneService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class DroneHandler {

    private final DroneService droneService;

    public DroneHandler(DroneService droneService) {
        this.droneService = droneService;
    }

    @ApiOperation(value = "Register a new drone")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DroneDTO.class),
            @ApiResponse(code = 400, message = "Invalid request")
    })
    public Mono<ServerResponse> register(ServerRequest request) {
        Mono<DroneDTO> droneMono = request.bodyToMono(DroneDTO.class);
        return droneMono.flatMap(drone -> {
            Mono<DroneDTO> registeredDroneMono = droneService.register(drone);
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(registeredDroneMono, DroneDTO.class);
        });
    }

    @ApiOperation(value = "Load medication items onto a drone")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = DroneDTO.class),
            @ApiResponse(code = 400, message = "Invalid request")
    })
    public Mono<ServerResponse> load(ServerRequest request) {
        Mono<DroneLoadRQ> droneLoadMono = request.bodyToMono(DroneLoadRQ.class);
        return droneLoadMono.flatMap(droneLoad -> {
            Mono<DroneDTO> loadedDroneMono = droneService.load(droneLoad.getDroneId(), droneLoad.getMedicationsIds());
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(loadedDroneMono, DroneDTO.class);
        });
    }

    @ApiOperation(value = "Get loaded medication items for a given drone")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MedicationDTO.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Invalid request")
    })
    public Mono<ServerResponse> getLoadedItems(ServerRequest request) {
        Long droneId = Long.parseLong(request.pathVariable("id"));
        Flux<MedicationDTO> loadedItemsFlux = droneService.getLoaded(droneId);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(loadedItemsFlux, MedicationDTO.class);
    }

    @ApiOperation(value = "Check available drones for loading")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class),
            @ApiResponse(code = 400, message = "Invalid request")
    })
    public Mono<ServerResponse> checkAvailableDrones(ServerRequest request) {
        Flux<DroneDTO> availableDronesFlux = droneService.getAvailable();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(availableDronesFlux.collectList(), new ParameterizedTypeReference<>() {
                });
    }

    @ApiOperation(value = "Check drone battery level for a given drone")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class),
            @ApiResponse(code = 400, message = "Invalid request")
    })
    public Mono<ServerResponse> checkBatteryLevel(ServerRequest request) {
        Long droneId = Long.valueOf(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(droneService.checkBatteryLevel(droneId), Integer.class);
    }
}