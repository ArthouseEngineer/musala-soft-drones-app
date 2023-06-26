package com.musala.drones.handlers;


import com.musala.drones.model.entity.UserEntity;
import com.musala.drones.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class UserHandler {

    private final UserRepository userRepository;

    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<UserEntity> userMono = request.bodyToMono(UserEntity.class).flatMap(userRepository::save);
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(userMono, UserEntity.class);
    }

    public Mono<ServerResponse> listUser(ServerRequest request) {
        Flux<UserEntity> user = userRepository.findAll();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(user, UserEntity.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        Long userId = Long.valueOf(request.pathVariable("userId"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<UserEntity> userMono = userRepository.findById(userId);
        return userMono.flatMap(userEntity -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .body(BodyInserters.fromValue(userEntity)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        Long userId = Long.valueOf(request.pathVariable("userId"));
        userRepository.deleteById(userId);
        return ServerResponse.ok().build();
    }

}
