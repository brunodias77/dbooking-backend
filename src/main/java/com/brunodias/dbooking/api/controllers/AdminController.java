package com.brunodias.dbooking.api.controllers;

import com.brunodias.dbooking.application.dtos.RoomDTO;
import com.brunodias.dbooking.domain.services.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IRoomService _roomService;
    // BOOKINGS

    @GetMapping("/all-bookings")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity getAllBookings() {
        return ResponseEntity.ok("OK GOOGLE");
    }

    // ROOMS

    @PostMapping("/add/new-room")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoomDTO> addNewRoom(
            @RequestParam("roomPrice") BigDecimal roomPrice,
            @RequestParam("numberOfGuest") int numberOfGuest,
            @RequestParam("numberOfGuest") int doubleBed,
             @RequestParam("numberOfGuest") int singleBed,
             @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam("isBooked") boolean isBooked,
            @RequestParam("photos") List<MultipartFile> photos,
            @RequestParam(required = false) List<Integer> ratings
    ) throws SQLException, IOException {

        var response = _roomService.addNewRoom(roomPrice,numberOfGuest, doubleBed, singleBed, description, location, isBooked, photos, ratings);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/room/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteRoom(@PathVariable UUID roomId) {
        return ResponseEntity.ok("OK GOOGLE");
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity updateRoom(@PathVariable Long roomId,
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) BigDecimal roomPrice,
            @RequestParam(required = false) MultipartFile photo) throws SQLException, IOException {
        return ResponseEntity.ok("OK GOOGLE");
    }

    // USERS

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity getUsers() {

        return ResponseEntity.ok("OK GOOGLE");
    }

}
