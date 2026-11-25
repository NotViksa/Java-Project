
package com.tu.javaproject.mapper;

import com.tu.javaproject.dto.CreateReservationRequest;
import com.tu.javaproject.dto.ReservationDto;
import com.tu.javaproject.model.Reservation;
import com.tu.javaproject.model.Room;
import com.tu.javaproject.model.Guest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(target = "guestId", source = "guest.id")
    @Mapping(target = "guestName",
        expression = "java(reservation.getGuest().getFirstName() + \" \" + reservation.getGuest().getLastName())")
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "roomNumber", source = "room.roomNumber")
    @Mapping(target = "hotelId", source = "room.hotel.id")
    @Mapping(target = "hotelName", source = "room.hotel.name")
    ReservationDto toDto(Reservation reservation);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "room", source = "room")
    @Mapping(target = "guest", source = "guest")
    @Mapping(target = "checkInDate", source = "request.checkInDate")
    @Mapping(target = "checkOutDate", source = "request.checkOutDate")
    @Mapping(target = "guestCount", source = "request.guestCount")
    Reservation toEntity(
        CreateReservationRequest request,
        Room room,
        Guest guest
    );
}
