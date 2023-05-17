package com.musala.drones.config;

import com.musala.drones.handler.AuthHandler;
import com.musala.drones.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Component
@RequiredArgsConstructor
public class UsersRouter {

    private final  UserHandler userHandler;
    @Bean
    public RouterFunction<ServerResponse> userRoute() {
        return RouterFunctions
                .route(POST("/users").and(accept(APPLICATION_JSON)), userHandler::createUser)
                .andRoute(GET("/users").and(accept(APPLICATION_JSON)), userHandler::listUser)
                .andRoute(GET("/users/{userId}").and(accept(APPLICATION_JSON)), userHandler::getUserById)
                .andRoute(DELETE("/users/userId").and(accept(APPLICATION_JSON)), userHandler::deleteUser);
    }

}
