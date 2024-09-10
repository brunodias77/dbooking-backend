package com.brunodias.dbooking.application.services;

import com.brunodias.dbooking.application.dtos.BookedRoomDTO;
import com.brunodias.dbooking.application.exceptions.InvalidBookingRequestException;
import com.brunodias.dbooking.domain.entities.BookedRoom;
import com.brunodias.dbooking.domain.entities.Room;
import com.brunodias.dbooking.domain.services.IBookingService;
import com.brunodias.dbooking.infrastructure.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository _bookingRepository;
    private final RoomService _roomService;

    @Override
    public void cancelBooking(UUID bookingId) {

    }

    @Override
    public List<BookedRoomDTO> getAllBookingsByRoomId(UUID roomId) {
        return List.of();
    }


    @Override
    public String saveBooking(UUID roomId, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("A data de check-in deve ser anterior à data de check-out");
        }
        var room = _roomService.getRoomById(roomId).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        if (roomIsAvailable) {
            room.addBooking(bookingRequest);
            _bookingRepository.save(bookingRequest);
        } else {
            throw new InvalidBookingRequestException("Desculpe, este quarto não está disponível para as datas selecionadas");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    @Override
    public BookedRoomDTO findByBookingConfirmationCode(String confirmationCode) {
        return null;
    }

    @Override
    public List<BookedRoomDTO> getAllBookings() {
        return List.of();
    }

    @Override
    public List<BookedRoomDTO> getBookingsByUserEmail(String email) {
        return List.of();
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                        || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                        || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                        && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                        && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                        || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                        && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                        && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                        || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                        && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate())));
    }
}