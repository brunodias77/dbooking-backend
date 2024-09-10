package com.brunodias.dbooking.application.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record BookedRoomDTO(
        LocalDate checkInDate,
        LocalDate checkOutDate,
        int numOfAdults,
        int numOfChildren,
        int totalNumOfGuest,
        String bookingConfirmationCode,
        UUID roomId,
        UUID userId
) {
}
