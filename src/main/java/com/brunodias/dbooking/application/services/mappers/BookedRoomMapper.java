package com.brunodias.dbooking.application.services.mappers;

import com.brunodias.dbooking.application.dtos.BookedRoomDTO;
import com.brunodias.dbooking.domain.entities.BookedRoom;

public class BookedRoomMapper {
    // Converte de BookedRoom para BookedRoomDTO
    public static BookedRoomDTO toDTO(BookedRoom bookedRoom) {
        return new BookedRoomDTO(
                bookedRoom.getCheckInDate(),
                bookedRoom.getCheckOutDate(),
                bookedRoom.getNumOfAdults(),
                bookedRoom.getNumOfChildren(),
                bookedRoom.getTotalNumOfGuest(),
                bookedRoom.getBookingConfirmationCode(),
                bookedRoom.getRoom().getId(),
                bookedRoom.getUser().getId()
        );
    }

    // Converte de BookedRoomDTO para BookedRoom
    public static BookedRoom toEntity(BookedRoomDTO bookedRoomDTO) {
        BookedRoom bookedRoom = new BookedRoom();
        bookedRoom.setCheckInDate(bookedRoomDTO.checkInDate());
        bookedRoom.setCheckOutDate(bookedRoomDTO.checkOutDate());
        bookedRoom.setNumOfAdults(bookedRoomDTO.numOfAdults());
        bookedRoom.setNumOfChildren(bookedRoomDTO.numOfChildren());
        bookedRoom.setTotalNumOfGuest(bookedRoomDTO.totalNumOfGuest());
        bookedRoom.setBookingConfirmationCode(bookedRoomDTO.bookingConfirmationCode());

        // Aqui você precisaria associar as entidades Room e User pelas IDs fornecidas no DTO.
        // Exemplo de como pode ser feito (dependendo da sua estrutura):
        // Room room = roomRepository.findById(bookedRoomDTO.roomId()).orElseThrow(() -> new EntityNotFoundException("Room not found"));
        // User user = userRepository.findById(bookedRoomDTO.userId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        // bookedRoom.setRoom(room);
        // bookedRoom.setUser(user);

        return bookedRoom;
    }
}
