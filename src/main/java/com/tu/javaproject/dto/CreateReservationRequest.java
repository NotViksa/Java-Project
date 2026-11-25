
package com.tu.javaproject.dto;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public record CreateReservationRequest(

    @NotNull
    @Schema(description = "Room ID")
    Long roomId,

    @NotNull
    @Schema(description = "Guest ID")
    Long guestId,

    @NotNull
    @FutureOrPresent
    @Schema(description = "Check-in date")
    LocalDate checkInDate,

    @NotNull
    @Schema(description = "Check-out date")
    LocalDate checkOutDate,

    @NotNull
    @Min(1)
    @Schema(description = "Number of guests")
    Integer guestCount

) {

    @AssertTrue(message = "Check-out must be after check-in")
    @Schema(hidden = true)
    public boolean isDateRangeValid() {
        return checkInDate == null
            || checkOutDate == null
            || checkOutDate.isAfter(checkInDate);
    }
}
