package com.tu.javaproject.repository;

import com.tu.javaproject.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByHotelId(Long hotelId);

    Optional<Room> findByHotelIdAndRoomNumber(Long hotelId, String roomNumber);

    boolean existsByHotelId(Long hotelId);
}
