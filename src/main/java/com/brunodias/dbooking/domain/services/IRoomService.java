package com.brunodias.dbooking.domain.services;

import com.brunodias.dbooking.application.dtos.RoomDTO;
import com.brunodias.dbooking.domain.entities.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface IRoomService {
    
    RoomDTO addNewRoom(
                     BigDecimal roomPrice,
                    int numberOfGuest,
                    int doubleBed,
                    int singleBed,
                    String description,
                     String location,
                     boolean isBooked,
                     List<MultipartFile> photos,
                     List<Integer> ratings
                     ) throws SQLException, IOException;
}
