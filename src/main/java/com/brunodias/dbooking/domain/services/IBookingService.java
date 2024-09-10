package com.brunodias.dbooking.domain.services;

import com.brunodias.dbooking.application.dtos.BookedRoomDTO;
import com.brunodias.dbooking.domain.entities.BookedRoom;

import java.util.List;
import java.util.UUID;

public interface IBookingService {
    void cancelBooking(UUID bookingId);

    List<BookedRoomDTO> getAllBookingsByRoomId(UUID roomId);

    String saveBooking(UUID roomId, BookedRoom bookingRequest);

    BookedRoomDTO findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoomDTO> getAllBookings();

    List<BookedRoomDTO> getBookingsByUserEmail(String email);
}
