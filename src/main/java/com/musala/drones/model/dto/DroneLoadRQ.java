package com.musala.drones.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class DroneLoadRQ {
    private Long droneId;
    private List<Long> medicationsIds;
}
