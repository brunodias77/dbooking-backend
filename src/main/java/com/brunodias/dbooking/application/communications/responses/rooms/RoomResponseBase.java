package com.brunodias.dbooking.application.communications.responses.rooms;

import java.math.BigDecimal;

public record RoomResponseBase(
        String roomType,
        BigDecimal roomPrice,
        boolean isBooked,
        String photo
// List<BookingResponse> bookings
) {
    public RoomResponseBase(String roomType, BigDecimal roomPrice) {
        this(roomType, roomPrice, false, null);
    }

    public RoomResponseBase(String roomType, BigDecimal roomPrice, boolean isBooked) {
        this(roomType, roomPrice, isBooked, null);
    }
}
