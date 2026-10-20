package com.tu.javaproject.repository;

import com.tu.javaproject.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByGuestId(Long guestId);

    boolean existsByGuestId(Long guestId);

    boolean existsByRoomId(Long roomId);
}
