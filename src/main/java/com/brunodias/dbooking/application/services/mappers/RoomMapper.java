package com.brunodias.dbooking.application.services.mappers;

import java.util.stream.Collectors;

import com.brunodias.dbooking.application.communications.responses.rooms.RoomResponseBase;
import com.brunodias.dbooking.domain.entities.Room;

public class RoomMapper {

    public static RoomResponseBase copyEntityToRoomResponseBase(Room room) {
        if (room == null) {
            return null;
        }

        return new RoomResponseBase(
                room.getRoomType(),
                room.getRoomPrice(),
                room.getIsBooked(),
                room.getLocation(),
                room.getDescription(),
                room.getPhotos(),
                room.getRatings());
    }
}
