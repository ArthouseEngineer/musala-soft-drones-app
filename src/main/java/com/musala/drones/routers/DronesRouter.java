package com.musala.drones.routers;

import com.musala.drones.handlers.DroneHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
@RequiredArgsConstructor
public class DronesRouter {

    private final DroneHandler droneHandler;

    @Bean
    public RouterFunction<ServerResponse> droneRoutes() {
        return RouterFunctions.route()
                .POST("/drone/register", droneHandler::register)
                .POST("/drone/load", droneHandler::load)
                .GET("/drone/{id}/medications", droneHandler::getLoadedItems)
                .GET("/drone/available", droneHandler::checkAvailableDrones)
                .GET("/drone/{id}/battery-level", droneHandler::checkBatteryLevel)
                .build();
    }
}
