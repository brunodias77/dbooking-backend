package com.brunodias.dbooking.domain.services;

import com.brunodias.dbooking.application.communications.responses.rooms.RoomResponseBase;
import com.brunodias.dbooking.domain.entities.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRoomService {

    RoomResponseBase addNewRoom(String roomType, BigDecimal roomPrice, String location, String description,
            List<MultipartFile> photos, List<Integer> ratings) throws SQLException, IOException;

    List<String> getAllRoomTypes();

    List<RoomResponseBase> getAllRooms();

    void deleteRoom(UUID roomId);

    RoomResponseBase updateRoom(UUID roomId, String roomType, BigDecimal roomPrice, MultipartFile photo)
            throws IOException;

    Optional<Room> getRoomById(UUID roomId);

    List<RoomResponseBase> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType);

}
