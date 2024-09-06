package com.brunodias.dbooking.application.services.mappers;

import java.util.stream.Collectors;

import com.brunodias.dbooking.application.communications.responses.rooms.RoomResponseBase;
import com.brunodias.dbooking.application.dtos.RoomDTO;
import com.brunodias.dbooking.domain.entities.Room;

public class RoomMapper {

    public static RoomDTO roomToRoomDTO(Room room) {
        if (room == null) {
            return null;
        }

        return new RoomDTO(
                room.getRoomPrice(),
                room.getNumberOfGuest(),
                room.getDoubleBed(),
                room.getSingleBed(),
                room.getIsBooked(),
                room.getLocation(),
                room.getDescription(),
                room.getPhotos(),
                room.getRatings()
        );
    }

    public static Room roomDTOToRoom(RoomDTO roomDTO) {
        if (roomDTO == null) {
            return null;
        }

        Room room = new Room();
        room.setRoomPrice(roomDTO.roomPrice());
        room.setNumberOfGuest(roomDTO.numberOfGuest());
        room.setDoubleBed(roomDTO.doubleBed());
        room.setSingleBed(roomDTO.singleBed());
        room.setBooked(roomDTO.isBooked());
        room.setLocation(roomDTO.location());
        room.setDescription(roomDTO.description());
        room.setPhotos(roomDTO.photos());
        room.setRatings(roomDTO.ratings());

        return room;
    }
}
