package com.musala.drones.model.dto;

public record ApiResponse(int status, String message, Object result) {
}
