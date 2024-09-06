package com.brunodias.dbooking.application.services;

import com.brunodias.dbooking.application.dtos.RoomDTO;
import com.brunodias.dbooking.application.services.mappers.RoomMapper;
import com.brunodias.dbooking.domain.entities.Room;
import com.brunodias.dbooking.domain.services.IFileService;
import com.brunodias.dbooking.domain.services.IRoomService;
import com.brunodias.dbooking.infrastructure.repositories.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {

    private final RoomRepository _roomRepository;
    private final IFileService _fileService;

    @Override
    public RoomDTO addNewRoom(BigDecimal roomPrice, int numberOfGuest, int doubleBed, int singleBed, String description, String location, boolean isBooked, List<MultipartFile> photos, List<Integer> ratings) throws SQLException, IOException {

        var room = new Room();
        room.setRoomPrice(roomPrice);
        room.setNumberOfGuest(numberOfGuest);
        room.setDoubleBed(doubleBed);
        room.setSingleBed(singleBed);
        room.setDescription(description);
        room.setLocation(location);
        room.setBooked(isBooked);
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
        var roomDTO = RoomMapper.roomToRoomDTO(roomSaved);
        return roomDTO;
    }

    @Transactional
    @Override
    public List<RoomDTO> getAllRooms() {
        var rooms = _roomRepository.findAllRoomsWithPhotosAndRatings();
        var listRoomDTO = new ArrayList<RoomDTO>();
        for(Room room : rooms){
            var roomDTO = RoomMapper.roomToRoomDTO(room);
            listRoomDTO.add(roomDTO);
        }
        return listRoomDTO;
    }

    @Override
    public List<RoomDTO> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, int numberOfGuest) {
        var rooms = _roomRepository.findAvailableRooms(numberOfGuest, checkOutDate, checkInDate);
        var listRoomDTO = new ArrayList<RoomDTO>();
        for(Room room : rooms){
            var roomDTO = RoomMapper.roomToRoomDTO(room);
            listRoomDTO.add(roomDTO);
        }
        return listRoomDTO;
    }
}
