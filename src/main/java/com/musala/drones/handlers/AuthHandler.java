package com.musala.drones.handlers;

import com.musala.drones.model.entity.UserEntity;
import com.musala.drones.model.dto.ApiResponse;
import com.musala.drones.model.dto.LoginRequest;
import com.musala.drones.model.dto.LoginResponse;
import com.musala.drones.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class AuthHandler {

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final UserRepository userRepository;


    public Mono<ServerResponse> login(ServerRequest request) {
        Mono<LoginRequest> loginRequest = request.bodyToMono(LoginRequest.class);
        return loginRequest.flatMap(login -> userRepository.findByUsername(login.getUsername())
                .flatMap(userEntity -> {
                    if (passwordEncoder.matches(login.getPassword(), userEntity.getPassword())) {
                        return ServerResponse.ok().contentType(APPLICATION_JSON).body(BodyInserters.fromValue(new ApiResponse(HttpStatus.OK.value(), "Successful login", new LoginResponse(tokenProvider.generateToken(userEntity)))));
                    } else {
                        return ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Invalid credentials", null)));
                    }
                })
                .switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "User does not exist", null)))));
    }

    public Mono<ServerResponse> signUp(ServerRequest request) {
        Mono<UserEntity> userMono = request.bodyToMono(UserEntity.class);
        return userMono.map(userEntity -> {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            return userEntity;
        }).flatMap(userEntity -> userRepository.findByUsername(userEntity.getUsername())
                .flatMap(dbUserEntity -> ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "User already exist", null))))
                .switchIfEmpty(userRepository.save(userEntity).flatMap(savedUserEntity -> ServerResponse.ok().contentType(APPLICATION_JSON).body(BodyInserters.fromValue(savedUserEntity)))));
    }
}
