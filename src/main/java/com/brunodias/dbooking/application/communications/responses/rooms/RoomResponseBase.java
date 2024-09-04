package com.brunodias.dbooking.application.communications.responses.rooms;

import java.math.BigDecimal;
import java.util.List;

public record RoomResponseBase(
        String roomType,
        BigDecimal roomPrice,
        boolean isBooked,
        String location,
        String description,
        List<String> photos,
        List<Integer> ratings) {

}
