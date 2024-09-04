package com.brunodias.dbooking.application.services;

import com.brunodias.dbooking.application.communications.responses.rooms.RoomResponseBase;
import com.brunodias.dbooking.application.services.mappers.RoomMapper;
import com.brunodias.dbooking.domain.entities.Room;
import com.brunodias.dbooking.domain.services.IFileService;
import com.brunodias.dbooking.domain.services.IRoomService;
import com.brunodias.dbooking.infrastructure.repositories.IRoomRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.hibernate.Hibernate;
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
    public RoomResponseBase addNewRoom(String roomType, BigDecimal roomPrice, String location, String description,
            List<MultipartFile> photos, List<Integer> ratings) throws SQLException, IOException {

        var room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        room.setLocation(location);
        room.setDescription(description);
        if (ratings != null) {
            for (Integer rating : ratings) {
                room.addRating(rating);
            }
        }
        for (MultipartFile photo : photos) {
            if (!photo.isEmpty()) {
                // Salva o arquivo e obtém o caminho
                String filePath = _fileService.saveFile(photo);
                // Cria a URL da imagem
                String fileUrl = "/uploads/" + new File(filePath).getName();
                // Define o caminho do arquivo como a foto do objeto 'room'
                room.addPhoto(fileUrl);
            }
        }
        var roomSaved = _roomRepository.save(room);
        var response = RoomMapper.copyEntityToRoomResponseBase(roomSaved);
        return response;
    }

    @Transactional
    @Override
    public List<RoomResponseBase> getAvailableRooms(LocalDate checkInDate,
            LocalDate checkOutDate, String roomType) {
        var rooms = _roomRepository.findAvailableRoomsByDatesAndType(checkInDate,
                checkOutDate, roomType);
        var response = new ArrayList<RoomResponseBase>();
        for (Room room : rooms) {
            // Inicializar a coleção photos
            Hibernate.initialize(room.getPhotos());
            // Inicializar a coleção ratings
            Hibernate.initialize(room.getRatings());

            // Cria uma nova lista para armazenar as URLs das fotos
            List<String> updatedPhotos = new ArrayList<>();
            for (String photo : room.getPhotos()) {
                updatedPhotos.add(photo); // Adiciona a URL à nova lista
            }
            // Atualiza as fotos do quarto com a nova lista de URLs
            room.setPhotos(updatedPhotos);
            var newRoomResponse = RoomMapper.copyEntityToRoomResponseBase(room);
            response.add(newRoomResponse);
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRoomTypes'");
    }

    @Override
    public List<RoomResponseBase> getAllRooms() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllRooms'");
    }

    @Override
    public void deleteRoom(UUID roomId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRoom'");
    }

    @Override
    public RoomResponseBase updateRoom(UUID roomId, String roomType, BigDecimal roomPrice, MultipartFile photo)
            throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRoom'");
    }

    @Override
    public Optional<Room> getRoomById(UUID roomId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRoomById'");
    }

    // @Override
    // public List<String> getAllRoomTypes() {
    // return _roomRepository.findDistinctRoomTypes();
    // }

    // @Override
    // public List<RoomResponseBase> getAllRooms() {

    // var rooms = _roomRepository.findAll();
    // var response = new ArrayList<RoomResponseBase>();
    // for (Room room : rooms) {
    // var roomResponseBase = new RoomResponseBase(room.getRoomType(),
    // room.getRoomPrice(), room.getIsBooked(),
    // room.getPhoto());
    // response.add(roomResponseBase);
    // }
    // return response;
    // }

    // @Override
    // public void deleteRoom(UUID roomId) {
    // Optional<Room> room = _roomRepository.findById(roomId);
    // if (room.isPresent()) {
    // _roomRepository.deleteById(roomId);
    // }
    // }

    // @Override
    // public RoomResponseBase updateRoom(UUID roomId, String roomType, BigDecimal
    // roomPrice, MultipartFile photo)
    // throws IOException {

    // Room room = _roomRepository.findById(roomId)
    // .orElseThrow(() -> new IllegalArgumentException("Esse quarto nao foi
    // encontrado !"));

    // String filePath = _fileService.getUploadDir() + File.separator +
    // photo.getOriginalFilename();
    // if (!_fileService.isFileExists(filePath)) {
    // String savedFilePath = _fileService.saveFile(photo);
    // String fileUrl = "/uploads/" + new File(filePath).getName();
    // room.setPhoto(fileUrl);
    // }
    // if (roomType != null) {
    // room.setRoomType(roomType);
    // }
    // if (roomPrice != null) {
    // room.setRoomPrice(roomPrice);
    // }
    // var updatedRoom = _roomRepository.save(room);
    // var response = new RoomResponseBase(updatedRoom.getRoomType(),
    // updatedRoom.getRoomPrice(),
    // updatedRoom.getIsBooked(), updatedRoom.getPhotos());
    // return response;
    // }

    // @Override
    // public Optional<Room> getRoomById(UUID roomId) {
    // return Optional.of(_roomRepository.findById(roomId).get());
    // }

}
