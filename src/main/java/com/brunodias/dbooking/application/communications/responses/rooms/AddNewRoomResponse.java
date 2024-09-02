package com.brunodias.dbooking.application.communications.responses.rooms;

import java.math.BigDecimal;
import java.sql.Blob;

public record AddNewRoomResponse(
         String roomType,
         BigDecimal roomPrice,
         String photo) {

}
