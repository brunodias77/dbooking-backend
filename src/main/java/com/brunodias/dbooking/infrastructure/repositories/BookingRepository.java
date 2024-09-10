package com.brunodias.dbooking.infrastructure.repositories;

import com.brunodias.dbooking.domain.entities.BookedRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<BookedRoom, UUID> {

    List<BookedRoom> findByRoomId(UUID roomId);

    Optional<BookedRoom> findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> findByUserEmail(String email);
}
