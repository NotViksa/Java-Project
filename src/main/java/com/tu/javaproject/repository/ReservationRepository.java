package com.tu.javaproject.repository;

import com.tu.javaproject.model.Reservation;
import com.tu.javaproject.model.ReservationStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @EntityGraph(attributePaths = {"guest", "room", "room.hotel"})
    List<Reservation> findByGuestId(Long guestId);

    @EntityGraph(attributePaths = {"guest", "room", "room.hotel"})
    List<Reservation> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = {"guest", "room", "room.hotel"})
    @Query("select res from Reservation res where res.id = :id")
    Optional<Reservation> findByIdWithDetails(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select res from Reservation res where res.id = :id")
    Optional<Reservation> findByIdForUpdate(@Param("id") Long id);

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
