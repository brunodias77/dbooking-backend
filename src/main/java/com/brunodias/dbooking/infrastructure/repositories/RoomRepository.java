package com.brunodias.dbooking.infrastructure.repositories;

import com.brunodias.dbooking.domain.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {


    @Query("SELECT r, rp, rr FROM Room r LEFT JOIN r.photos rp LEFT JOIN r.ratings rr")
    List<Room> findAllRoomsWithPhotosAndRatings();

    @Query("SELECT r FROM Room r " +
            "WHERE r.numberOfGuest = :numberOfGuest " +
            "AND r.id NOT IN (" +
            "    SELECT br.room.id " +
            "    FROM BookedRoom br " +
            "    WHERE br.checkInDate <= :checkOutDate " +
            "    AND br.checkOutDate >= :checkInDate" +
            ")")
    List<Room> findAvailableRooms(@Param("numberOfGuest") int numberOfGuest,
                                  @Param("checkOutDate") LocalDate checkOutDate,
                                  @Param("checkInDate") LocalDate checkInDate);

}
