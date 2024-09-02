package com.brunodias.dbooking.infrastructure.repositories;

import com.brunodias.dbooking.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface IRoomRepository extends JpaRepository<Room, UUID> {
    @Query("SELECT DISTINCT r.roomType FROM Room r")
    List<String> findDistinctRoomTypes();
}
