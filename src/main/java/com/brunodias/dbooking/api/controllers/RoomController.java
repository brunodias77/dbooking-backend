package com.brunodias.dbooking.api.controllers;

import com.brunodias.dbooking.application.dtos.RoomDTO;
import com.brunodias.dbooking.domain.services.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService _roomService;

    @GetMapping("/all-rooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        var response = _roomService.getAllRooms();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/available-rooms")
    public ResponseEntity getAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam("numberOfGuest") int numberOfGuest) {
        var response = _roomService.getAvailableRooms(checkInDate, checkOutDate, numberOfGuest);
        return ResponseEntity.ok(response);
    }

}
