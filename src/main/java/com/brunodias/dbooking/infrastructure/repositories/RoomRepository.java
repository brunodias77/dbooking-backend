package com.brunodias.dbooking.infrastructure.repositories;

import com.brunodias.dbooking.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
}
