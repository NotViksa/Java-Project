
package com.tu.javaproject.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public record HotelDto(

    @Schema(description = "Hotel ID")
    Long id,

    @NotBlank
    @Schema(description = "Hotel name")
    String name,

    @NotBlank
    @Schema(description = "City")
    String city,

    @NotBlank
    @Schema(description = "Hotel address")
    String address,

    @Schema(description = "Hotel description")
    String description

) {
}
