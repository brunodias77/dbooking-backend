package com.brunodias.dbooking.domain.services;

import com.brunodias.dbooking.domain.entities.BookedRoom;

import java.util.List;
import java.util.UUID;

public interface IBookingService {
    void cancelBooking(UUID bookingId);
    List<BookedRoom> getAllBookingsByRoomId(Long roomId);
    String saveBooking(UUID roomId, BookedRoom bookingRequest);
    BookedRoom findByBookingConfirmationCode(String confirmationCode);
    List<BookedRoom> getAllBookings();
    List<BookedRoom> getBookingsByUserEmail(String email);
}
