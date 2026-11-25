
package com.tu.javaproject.service;

import com.tu.javaproject.dto.RoomDto;
import com.tu.javaproject.mapper.RoomMapper;
import com.tu.javaproject.model.*;
import com.tu.javaproject.repository.*;
import com.tu.javaproject.exception.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomExtraRepository roomExtraRepository;
    private final ReservationRepository reservationRepository;
    private final RoomMapper roomMapper;

    @Transactional(readOnly = true)
    public List<RoomDto> findAll(Long hotelId) {

        List<Room> rooms = hotelId == null
                ? roomRepository.findAll()
                : roomRepository.findByHotelId(hotelId);

        return rooms.stream()
                .map(roomMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomDto findById(Long id) {
        return roomMapper.toDto(getRoom(id));
    }

    public RoomDto create(RoomDto dto) {

        Hotel hotel = getHotel(dto.hotelId());

        validateRoomNumber(
                dto.hotelId(), dto.roomNumber(), null);

        Room room = roomMapper.toEntity(dto);

        room.setHotel(hotel);
        room.setExtras(getExtras(dto.extraIds()));

        return roomMapper.toDto(
                roomRepository.save(room));
    }

    public RoomDto update(Long id, RoomDto dto) {

        Room room = getRoom(id);
        Hotel hotel = getHotel(dto.hotelId());

        validateRoomNumber(
                dto.hotelId(), dto.roomNumber(), id);

        room.setRoomNumber(dto.roomNumber());
        room.setType(dto.type());
        room.setCapacity(dto.capacity());
        room.setPricePerNight(dto.pricePerNight());
        room.setHotel(hotel);
        room.setExtras(getExtras(dto.extraIds()));

        return roomMapper.toDto(
                roomRepository.save(room));
    }

    public void delete(Long id) {

        Room room = getRoom(id);

        if (reservationRepository.existsByRoomId(id)) {
            throw new ConflictException(
                    "Room has reservations and cannot be deleted");
        }

        roomRepository.delete(room);
    }

    @Transactional(readOnly = true)
    public List<RoomDto> findAvailable(
            Long hotelId,
            LocalDate checkInDate,
            LocalDate checkOutDate,
            int guestCount) {

        if (hotelId == null
                || checkInDate == null
                || checkOutDate == null
                || checkInDate.isBefore(LocalDate.now())
                || !checkOutDate.isAfter(checkInDate)
                || guestCount < 1) {

            throw new BadRequestException(
                    "Invalid dates or guest count");
        }

        getHotel(hotelId);

        return roomRepository.findAvailable(
                hotelId,
                checkInDate,
                checkOutDate,
                guestCount,
                ReservationStatus.CANCELLED)
                .stream()
                .map(roomMapper::toDto)
                .toList();
    }

    private Room getRoom(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() ->
                    new NotFoundException("Room not found"));
    }

    private Hotel getHotel(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() ->
                    new NotFoundException("Hotel not found"));
    }

    private Set<RoomExtra> getExtras(Set<Long> ids) {

        Set<RoomExtra> extras = new HashSet<>();

        if (ids != null) {
            for (Long id : ids) {
                extras.add(roomExtraRepository.findById(id)
                        .orElseThrow(() ->
                            new NotFoundException(
                                    "Extra not found: " + id)));
            }
        }

        return extras;
    }

    private void validateRoomNumber(
            Long hotelId,
            String roomNumber,
            Long excludedId) {

        roomRepository
                .findByHotelIdAndRoomNumber(hotelId, roomNumber)
                .filter(room -> !room.getId().equals(excludedId))
                .ifPresent(room -> {
                    throw new ConflictException(
                            "Room number already exists in this hotel");
                });
    }
}
