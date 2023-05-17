package com.musala.drones.handler;

import com.musala.drones.model.entity.User;
import com.musala.drones.model.dto.ApiResponse;
import com.musala.drones.model.dto.LoginRequest;
import com.musala.drones.model.dto.LoginResponse;
import com.musala.drones.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                .flatMap(user -> {
                    if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                        return ServerResponse.ok().contentType(APPLICATION_JSON).body(BodyInserters.fromValue(new ApiResponse(HttpStatus.OK.value(), "Successful login", new LoginResponse(tokenProvider.generateToken(user)))));
                    } else {
                        return ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Invalid credentials", null)));
                    }
                })
                .switchIfEmpty(ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "User does not exist", null)))));
    }

    public Mono<ServerResponse> signUp(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        return userMono.map(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return user;
        }).flatMap(user -> userRepository.findByUsername(user.getUsername())
                .flatMap(dbUser -> ServerResponse.badRequest().body(BodyInserters.fromValue(new ApiResponse(HttpStatus.BAD_REQUEST.value(), "User already exist", null))))
                .switchIfEmpty(userRepository.save(user).flatMap(savedUser -> ServerResponse.ok().contentType(APPLICATION_JSON).body(BodyInserters.fromObject(savedUser)))));
    }
}
