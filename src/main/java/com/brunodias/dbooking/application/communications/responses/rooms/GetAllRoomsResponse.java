package com.brunodias.dbooking.application.communications.responses.rooms;

import com.brunodias.dbooking.domain.entities.Room;

import java.util.ArrayList;
import java.util.List;

public record GetAllRoomsResponse(List<RoomResponseBase> rooms) {}
