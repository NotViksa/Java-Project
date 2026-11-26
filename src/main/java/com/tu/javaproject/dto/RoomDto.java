
package com.tu.javaproject.dto;

import com.tu.javaproject.model.RoomType;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Set;

public record RoomDto(

    @Schema(description = "Room ID")
    Long id,

    @NotBlank
    @Size(max = 20)
    @Schema(description = "Room number")
    String roomNumber,

    @NotNull
    @Schema(description = "Room type")
    RoomType type,

    @NotNull
    @Min(1)
    @Schema(description = "Maximum number of guests")
    Integer capacity,

    @NotNull
    @Positive
    @Digits(integer = 8, fraction = 2)
    @Schema(description = "Price per night")
    BigDecimal pricePerNight,

    @NotNull
    @Schema(description = "Hotel ID")
    Long hotelId,

    @Schema(description = "Room extra IDs")
    Set<@NotNull Long> extraIds

) {
}
