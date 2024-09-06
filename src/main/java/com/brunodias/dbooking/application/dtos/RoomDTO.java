package com.brunodias.dbooking.application.dtos;

import java.math.BigDecimal;
import java.util.List;

public record RoomDTO(
        BigDecimal roomPrice,
        int numberOfGuest,
        int doubleBed,
        int singleBed,
        boolean isBooked,
        String location,
        String description,
        List<String> photos,
        List<Integer> ratings
) {}