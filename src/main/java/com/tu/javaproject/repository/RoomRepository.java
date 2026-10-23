package com.tu.javaproject.repository;

import com.tu.javaproject.model.ReservationStatus;
import com.tu.javaproject.model.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelId(Long hotelId);

    Optional<Room> findByHotelIdAndRoomNumber(Long hotelId, String roomNumber);

    boolean existsByHotelId(Long hotelId);

    @Query("""
            select r from Room r
            where r.hotel.id = :hotelId
              and r.capacity >= :guestCount
              and not exists (
                  select res from Reservation res
                  where res.room = r
                    and res.status <> :cancelledStatus
                    and res.checkInDate < :checkOutDate
                    and res.checkOutDate > :checkInDate
              )
            order by r.roomNumber
            """)
    List<Room> findAvailable(@Param("hotelId") Long hotelId,
                             @Param("checkInDate") LocalDate checkInDate,
                             @Param("checkOutDate") LocalDate checkOutDate,
                             @Param("guestCount") int guestCount,
                             @Param("cancelledStatus") ReservationStatus cancelledStatus);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Room r where r.id = :id")
    Optional<Room> findByIdForUpdate(@Param("id") Long id);
}
