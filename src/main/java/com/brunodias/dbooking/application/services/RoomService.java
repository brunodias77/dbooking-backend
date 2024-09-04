package com.brunodias.dbooking.application.services;

import com.brunodias.dbooking.application.communications.responses.rooms.RoomResponseBase;
import com.brunodias.dbooking.domain.entities.Room;
import com.brunodias.dbooking.domain.services.IFileService;
import com.brunodias.dbooking.domain.services.IRoomService;
import com.brunodias.dbooking.infrastructure.repositories.IRoomRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final IRoomRepository _roomRepository;
    private final IFileService _fileService;

    @Override
    public Room addNewRoom(String roomType, BigDecimal roomPrice, MultipartFile file) throws SQLException, IOException {
        var room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!file.isEmpty()) {
            // Salva o arquivo e obtém o caminho
            String filePath = _fileService.saveFile(file);
            // Cria a URL da imagem
            String fileUrl = "/uploads/" + new File(filePath).getName();
            // Define o caminho do arquivo como a foto do objeto 'room'
            room.setPhoto(fileUrl);
        }
        return _roomRepository.save(room);

    }

    @Override
    public List<String> getAllRoomTypes() {
        return _roomRepository.findDistinctRoomTypes();
    }

    @Override
    public List<RoomResponseBase> getAllRooms() {

        var rooms = _roomRepository.findAll();
        var response = new ArrayList<RoomResponseBase>();
        for (Room room : rooms) {
            var roomResponseBase = new RoomResponseBase(room.getRoomType(), room.getRoomPrice(), room.getIsBooked(),
                    room.getPhoto());
            response.add(roomResponseBase);
        }
        return response;
    }

    @Override
    public void deleteRoom(UUID roomId) {
        Optional<Room> room = _roomRepository.findById(roomId);
        if (room.isPresent()) {
            _roomRepository.deleteById(roomId);
        }
    }

    @Override
    public RoomResponseBase updateRoom(UUID roomId, String roomType, BigDecimal roomPrice, MultipartFile photo)
            throws IOException {

        Room room = _roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Esse quarto nao foi encontrado !"));

        String filePath = _fileService.getUploadDir() + File.separator + photo.getOriginalFilename();
        if (!_fileService.isFileExists(filePath)) {
            String savedFilePath = _fileService.saveFile(photo);
            String fileUrl = "/uploads/" + new File(filePath).getName();
            room.setPhoto(fileUrl);
        }
        if (roomType != null) {
            room.setRoomType(roomType);
        }
        if (roomPrice != null) {
            room.setRoomPrice(roomPrice);
        }
        var updatedRoom = _roomRepository.save(room);
        var response = new RoomResponseBase(updatedRoom.getRoomType(), updatedRoom.getRoomPrice(),
                updatedRoom.getIsBooked(), updatedRoom.getPhoto());
        return response;
    }

    @Override
    public Optional<Room> getRoomById(UUID roomId) {
        return Optional.of(_roomRepository.findById(roomId).get());
    }

    @Override
    public List<RoomResponseBase> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        var rooms = _roomRepository.findAvailableRoomsByDatesAndType(checkInDate, checkOutDate, roomType);
        var response = new ArrayList<RoomResponseBase>();
        for (Room room : rooms){
            String photoUrl = "http://localhost:8080" + room.getPhoto();
            var newRoomResponse = new RoomResponseBase(room.getRoomType(), room.getRoomPrice(), room.getIsBooked(), photoUrl);
            response.add(newRoomResponse);
        }
        return response;
    }

}
