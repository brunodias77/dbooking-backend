package com.brunodias.dbooking.application.communications.requests.rooms;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Optional;

public record AddNewRoomRequest(
                MultipartFile photo,
                String roomType,
                BigDecimal roomPrice) {
}
