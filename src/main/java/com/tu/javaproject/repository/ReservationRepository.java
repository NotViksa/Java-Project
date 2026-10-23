package com.tu.javaproject.repository;

import com.tu.javaproject.model.Reservation;
import com.tu.javaproject.model.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByGuestId(Long guestId);

    boolean existsByGuestId(Long guestId);

    boolean existsByRoomId(Long roomId);

    @Query("""
            select count(res) from Reservation res
            where res.room.id = :roomId
              and res.status <> :cancelledStatus
              and res.checkInDate < :checkOutDate
              and res.checkOutDate > :checkInDate
            """)
    long countOverlapping(@Param("roomId") Long roomId,
                          @Param("checkInDate") LocalDate checkInDate,
                          @Param("checkOutDate") LocalDate checkOutDate,
                          @Param("cancelledStatus") ReservationStatus cancelledStatus);
}
