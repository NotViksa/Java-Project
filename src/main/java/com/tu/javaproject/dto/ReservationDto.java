
package com.tu.javaproject.dto;

import com.tu.javaproject.model.ReservationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Reservation details")
public record ReservationDto(

    @Schema(description = "Reservation ID")
    Long id,

    @Schema(description = "Guest ID")
    Long guestId,

    @Schema(description = "Guest full name")
    String guestName,

    @Schema(description = "Room ID")
    Long roomId,

    @Schema(description = "Room number")
    String roomNumber,

    @Schema(description = "Hotel ID")
    Long hotelId,

    @Schema(description = "Hotel name")
    String hotelName,

    LocalDate checkInDate,

    LocalDate checkOutDate,

    int guestCount,

    BigDecimal totalPrice,

    ReservationStatus status,

    LocalDateTime createdAt

) {
}
