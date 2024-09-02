package com.brunodias.dbooking.api.controllers;

import com.brunodias.dbooking.application.exceptions.InvalidBookingRequestException;
import com.brunodias.dbooking.domain.entities.BookedRoom;
import com.brunodias.dbooking.domain.services.IBookingService;
import com.brunodias.dbooking.domain.services.IRoomService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final IBookingService _bookingService;
    private final IRoomService _roomService;

    @PostMapping("/room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable UUID roomId, @RequestBody BookedRoom request){
        try{
            String confirmationCode = _bookingService.saveBooking(roomId, request);
            return ResponseEntity.ok("Quarto reservado com sucesso, seu código de confirmação de reserva é :"+confirmationCode);
        }catch (InvalidBookingRequestException err){
            return ResponseEntity.badRequest().body(err.getMessage());
        }
    }
}
