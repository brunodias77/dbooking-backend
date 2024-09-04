package com.brunodias.dbooking.api.controllers;

import com.brunodias.dbooking.application.communications.responses.rooms.AddNewRoomResponse;
import com.brunodias.dbooking.application.communications.responses.rooms.RoomResponseBase;
import com.brunodias.dbooking.application.exceptions.ResourceNotFoundException;
import com.brunodias.dbooking.domain.entities.Room;
import com.brunodias.dbooking.domain.services.IRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService _roomService;

    @PostMapping("/add/new-room")
    public ResponseEntity addNewRoom(@RequestParam String roomType,
            @RequestParam BigDecimal roomPrice,
            @RequestParam String location,
            @RequestParam String description,
            @RequestParam List<MultipartFile> photos,
            @RequestParam(required = false) List<Integer> ratings) throws SQLException, IOException {
        var response = _roomService.addNewRoom(roomType, roomPrice, location, description, photos, ratings);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available-rooms")
    public ResponseEntity<List<RoomResponseBase>> getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam("roomType") String roomType) throws SQLException {

        var response = _roomService.getAvailableRooms(checkInDate, checkOutDate, roomType);
        if (response.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(response);
        }
    }

    // @GetMapping("/all-rooms")
    // public ResponseEntity<List<RoomResponseBase>> getAllRooms() throws
    // SQLException {
    // var response = _roomService.getAllRooms();
    // return ResponseEntity.ok(response);
    // }

    // @GetMapping("/room/types")
    // public List<String> getRoomTypes() {
    // return _roomService.getAllRoomTypes();
    // }

    // @DeleteMapping("/delete/room/{roomId}")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // public ResponseEntity<Void> deleteRoom(@PathVariable UUID roomId) {
    // _roomService.deleteRoom(roomId);
    // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }

    // @PutMapping("/update/{roomId}")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    // public ResponseEntity<RoomResponseBase> updateRoom(@PathVariable UUID roomId,
    // @RequestParam(required = false) String roomType,
    // @RequestParam(required = false) BigDecimal roomPrice,
    // @RequestParam(required = false) MultipartFile photo) throws SQLException,
    // IOException {
    // var response = _roomService.updateRoom(roomId, roomType, roomPrice, photo);
    // return ResponseEntity.ok(response);
    // }

    // @GetMapping("/room/{roomId}")
    // public ResponseEntity<Optional<RoomResponseBase>> getRoomById(@PathVariable
    // UUID roomId) {
    // Optional<Room> room = _roomService.getRoomById(roomId);
    // return room.map(item -> {
    // RoomResponseBase roomResponseBase = new RoomResponseBase(item.getRoomType(),
    // item.getRoomPrice(),
    // item.getIsBooked(), item.getPhoto());
    // return ResponseEntity.ok(Optional.of(roomResponseBase));
    // }).orElseThrow(() -> new ResourceNotFoundException("Quarto nao encontrado"));
    // }

}