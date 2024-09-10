package com.brunodias.dbooking.api.controllers;

import com.brunodias.dbooking.domain.entities.BookedRoom;
import com.brunodias.dbooking.domain.services.IBookingService;
import com.brunodias.dbooking.domain.services.IRoomService;
import com.brunodias.dbooking.infrastructure.configurations.security.users.ApplicationUserDetaillsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final IBookingService _bookingService;
    private final IRoomService _roomService;


    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<String> saveBooking(@PathVariable UUID roomId, @RequestBody BookedRoom request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var response = _bookingService.saveBooking(roomId,request);
        return ResponseEntity.ok(response);
    }
}
