package com.brunodias.dbooking.application.services;

import com.brunodias.dbooking.application.exceptions.InvalidBookingRequestException;
import com.brunodias.dbooking.domain.entities.BookedRoom;
import com.brunodias.dbooking.domain.entities.Room;
import com.brunodias.dbooking.domain.services.IBookingService;
import com.brunodias.dbooking.domain.services.IRoomService;
import com.brunodias.dbooking.infrastructure.repositories.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService implements IBookingService {

    private final BookingRepository _bookingRepository;
    private final IRoomService _roomService;

    @Override
    public void cancelBooking(UUID bookingId) {

    }

    @Override
    public List<BookedRoom> getAllBookingsByRoomId(Long roomId) {
        return List.of();
    }

    @Override
    public String saveBooking(UUID roomId, BookedRoom bookingRequest) {
        if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
            throw new InvalidBookingRequestException("A data de check-in deve ser anterior à data de check-out");
        }
        Room room = _roomService.getRoomById(roomId).get();
        List<BookedRoom> existingBookings = room.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest, existingBookings);
        if (roomIsAvailable) {
            room.addBooking(bookingRequest);
            _bookingRepository.save(bookingRequest);

        } else {
            throw new InvalidBookingRequestException(
                    "Desculpe, este quarto não está disponível para as datas selecionadas");
        }
        return bookingRequest.getBookingConfirmationCode();
    }

    @Override
    public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
        return null;
    }

    @Override
    public List<BookedRoom> getAllBookings() {
        return List.of();
    }

    @Override
    public List<BookedRoom> getBookingsByUserEmail(String email) {
        return List.of();
    }

    private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking -> isCheckInDateConflict(bookingRequest, existingBooking) ||
                        isCheckOutDateConflict(bookingRequest, existingBooking) ||
                        isCheckInDateBetweenExistingBooking(bookingRequest, existingBooking) ||
                        isBookingCoversExistingBooking(bookingRequest, existingBooking) ||
                        isBookingStartsWhenExistingEnds(bookingRequest, existingBooking) ||
                        isBookingDatesMatchExactly(bookingRequest, existingBooking));
    }

    private boolean isCheckInDateConflict(BookedRoom bookingRequest, BookedRoom existingBooking) {
        return bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate());
    }

    private boolean isCheckOutDateConflict(BookedRoom bookingRequest, BookedRoom existingBooking) {
        return bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate());
    }

    private boolean isCheckInDateBetweenExistingBooking(BookedRoom bookingRequest, BookedRoom existingBooking) {
        return bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate());
    }

    private boolean isBookingCoversExistingBooking(BookedRoom bookingRequest, BookedRoom existingBooking) {
        return (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate())) ||
                (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())
                        && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()));
    }

    private boolean isBookingStartsWhenExistingEnds(BookedRoom bookingRequest, BookedRoom existingBooking) {
        return bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate());
    }

    private boolean isBookingDatesMatchExactly(BookedRoom bookingRequest, BookedRoom existingBooking) {
        return bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate());
    }

}
